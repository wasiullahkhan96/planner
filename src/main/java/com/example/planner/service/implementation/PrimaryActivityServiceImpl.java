package com.example.planner.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.planner.dto.ActivityDTO;
import com.example.planner.mapper.ActivityMapper;
import com.example.planner.model.Activity;
import com.example.planner.repository.ActivityRepository;
import com.example.planner.service.ActivityService;

@Service
public class PrimaryActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper; 

    @Override
    public List<ActivityDTO> getAllActivities(String name, LocalDate date, Boolean available) {
        List<Activity> activities;
        

        // Convert Activity to ActivityDTO if needed
        // return activities.stream()
        //     .map(activityMapper::toDTO)
        //     .collect(Collectors.toList());

        return new ArrayList<>();
    }

    @Override
    // Get a single activity by ID
    public Optional<ActivityDTO> getActivityById(Long id) {
        return activityRepository.findById(id)
                .map(activityMapper::toDTO);
    }

    @Override
    // Create a new activity
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        Activity activity = activityMapper.toEntity(activityDTO);
        Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toDTO(savedActivity);
    }

    @Override
    // Update an existing activity
    public Optional<ActivityDTO> updateActivity(Long id, ActivityDTO activityDTO) {
        return activityRepository.findById(id)
                .map(existingActivity -> {
                    existingActivity.setName(activityDTO.getName());
                    existingActivity.setLocation(activityDTO.getLocation());
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
    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
