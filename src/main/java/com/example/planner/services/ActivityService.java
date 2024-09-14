package com.example.planner.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.planner.DTOs.ActivityDTO;
import com.example.planner.mappers.ActivityMapper;
import com.example.planner.models.Activity;
import com.example.planner.repositories.ActivityRepository;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    // Get all activities
    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(ActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a single activity by ID
    public Optional<ActivityDTO> getActivityById(Long id) {
        return activityRepository.findById(id)
                .map(ActivityMapper::toDTO);
    }

    // Create a new activity
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        Activity activity = ActivityMapper.toEntity(activityDTO);
        Activity savedActivity = activityRepository.save(activity);
        return ActivityMapper.toDTO(savedActivity);
    }

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
                    return ActivityMapper.toDTO(updatedActivity);
                });
    }

    // Delete an activity by ID
    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
