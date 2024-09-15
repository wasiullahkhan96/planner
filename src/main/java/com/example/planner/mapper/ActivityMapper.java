package com.example.planner.mapper;

import org.springframework.stereotype.Component;

import com.example.planner.dto.ActivityDTO;
import com.example.planner.model.Activity;

@Component
public class ActivityMapper {

    // Converts Entity to DTO
    public ActivityDTO toDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .location(activity.getLocation())
                .date(activity.getDate())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .totalSeats(activity.getTotalSeats())
                .occupiedSeats(activity.getOccupiedSeats())
                .build();
    }

    // Converts DTO to Entity
    public Activity toEntity(ActivityDTO activityDTO) {
        return Activity.builder()
                .id(activityDTO.getId())  // If id needs to be set
                .name(activityDTO.getName())
                .location(activityDTO.getLocation())
                .date(activityDTO.getDate())
                .startTime(activityDTO.getStartTime())
                .endTime(activityDTO.getEndTime())
                .totalSeats(activityDTO.getTotalSeats())
                .occupiedSeats(activityDTO.getOccupiedSeats())
                .build();
    }
}
