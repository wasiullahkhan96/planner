package com.example.planner.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.planner.dto.ActivityDTO;
import com.example.planner.exceptions.DuplicateActivityNameException;
import com.example.planner.exceptions.InvalidDateRangeException;
import com.example.planner.exceptions.SeatLimitExceededException;
import com.example.planner.mapper.ActivityMapper;
import com.example.planner.model.Activity;
import com.example.planner.model.QActivity;
import com.example.planner.repository.ActivityRepository;
import com.example.planner.service.ActivityService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class PrimaryActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper; 

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QActivity activity = QActivity.activity;

    @Override
    public List<ActivityDTO> getAllActivities(String name, LocalDate date, Boolean available) {

        BooleanBuilder filter = new BooleanBuilder();

        if(name != null && !name.isEmpty())
            filter.and(activity.name.containsIgnoreCase(name));
        
        if(date != null)
            filter.and(activity.date.eq(date));

        if(available != null)
            if(available == true)
                filter.and(activity.totalSeats.gt(activity.occupiedSeats));
            else
                filter.and(activity.totalSeats.eq(activity.occupiedSeats));

        List<ActivityDTO> activitiesDTO = queryFactory
            .selectFrom(activity)
            .where(filter)
            .fetch()
            .stream()
            .map(activityMapper::toDTO)
            .collect(Collectors.toList());

        return activitiesDTO;

    }

    @Override
    // Get a single activity by ID
    public Optional<ActivityDTO> getActivityById(UUID id) {
        return activityRepository.findById(id)
                .map(activityMapper::toDTO);
    }

    @Override
    // Create a new activity
    public ActivityDTO createActivity(ActivityDTO activityDTO) {

        //Make sure that the number of total seat is less that occupied seats
        if(activityDTO.getTotalSeats() < activityDTO.getOccupiedSeats())
            throw new SeatLimitExceededException("Occupied seats cannot exceed total seats.");

        // Check if an activity with the same name already exists
        Optional<Activity> existingActivity = activityRepository.findByName(activityDTO.getName());
        if (existingActivity.isPresent()) {
            throw new DuplicateActivityNameException("An activity with the same name already exists.");
        }

        // Ensure that the start date is before the end date
        if (activityDTO.getStartTime().isAfter(activityDTO.getEndTime())) {
            throw new InvalidDateRangeException("Start date cannot be after the end date.");
    }

        Activity activity = activityMapper.toEntity(activityDTO);
        Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toDTO(savedActivity);
    }

    @Override
    // Update an existing activity
    public Optional<ActivityDTO> updateActivity(UUID id, ActivityDTO activityDTO) {

        //Make sure that the number of total seat is less that occupied seats
        if(activityDTO.getTotalSeats() < activityDTO.getOccupiedSeats()){
            throw new SeatLimitExceededException("Occupied seats cannot exceed total seats.");
        }

        // Ensure that the start date is before the end date
        if (activityDTO.getStartTime().isAfter(activityDTO.getEndTime())) {
            throw new InvalidDateRangeException("Start date cannot be after the end date.");
        }

        return activityRepository.findById(id)
                .map(existingActivity -> {
                    existingActivity.setName(activityDTO.getName());
                    existingActivity.setLocation(activityDTO.getLocation());
                    existingActivity.setDate(activityDTO.getDate());
                    existingActivity.setStartTime(activityDTO.getStartTime());
                    existingActivity.setEndTime(activityDTO.getEndTime());
                    existingActivity.setTotalSeats(activityDTO.getTotalSeats());
                    existingActivity.setOccupiedSeats(activityDTO.getOccupiedSeats());
                    Activity updatedActivity = activityRepository.save(existingActivity);
                    return activityMapper.toDTO(updatedActivity);
                });
    }

    @Override
    // Delete an activity by ID
    public boolean deleteActivity(UUID id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
