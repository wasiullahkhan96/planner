package com.example.planner.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import java.util.Optional;

import com.example.planner.dto.ActivityDTO;

public interface ActivityService {

    List<ActivityDTO> getAllActivities(String name, LocalDate date, Boolean available);

    ActivityDTO createActivity(ActivityDTO activityDTO);

    Optional<ActivityDTO> updateActivity(UUID id, ActivityDTO activityDTO);

    boolean deleteActivity(UUID id);

    // Other existing methods
    Optional<ActivityDTO> getActivityById(UUID id);
}
