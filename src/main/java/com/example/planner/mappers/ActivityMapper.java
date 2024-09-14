package com.example.planner.mappers;

import com.example.planner.DTOs.ActivityDTO;
import com.example.planner.models.Activity;

public class ActivityMapper {

    // Converts Entity to DTO
    public static ActivityDTO toDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .location(activity.getLocation())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .totalSeats(activity.getTotalSeats())
                .occupiedSeats(activity.getOccupiedSeats())
                .build();
    }

    // Converts DTO to Entity
    public static Activity toEntity(ActivityDTO activityDTO) {
        return Activity.builder()
                .id(activityDTO.getId())  // If id needs to be set
                .name(activityDTO.getName())
                .location(activityDTO.getLocation())
                .startTime(activityDTO.getStartTime())
                .endTime(activityDTO.getEndTime())
                .totalSeats(activityDTO.getTotalSeats())
                .occupiedSeats(activityDTO.getOccupiedSeats())
                .build();
    }
}
