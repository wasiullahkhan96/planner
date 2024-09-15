package com.example.planner.controllers;


import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.planner.dto.ActivityDTO;
import com.example.planner.service.ActivityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/activities")
@Tag(name = "Activity", description = "Operations related to activities")
public class ActivityController {

    // Here we inject the service, with the latest versions of Spring there is no 
    // for a explicit constructor injection
    @Autowired
    private ActivityService activityService;

    // Get all activities according to filters
    @Operation(summary = "Get all activities", description = "Fetch all activities according to filters.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved activities", content = @Content(schema = @Schema(implementation = List.class))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities(
            @Parameter(description = "Name to search by with contains match strategy", example = "Yoga class")
            @RequestParam(required = false) String name,

            @Parameter(description = "Date to search activities by", example = "2024-09-22")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @Parameter(description = "Looking for available activities", example = "true")
            @RequestParam(required = false) Boolean available) {

        List<ActivityDTO> activities = activityService.getAllActivities(name, date, available);
        return ResponseEntity.ok(activities);
    }

    // Get a specific activity by ID
    @Operation(summary = "Get activity by ID", description = "Fetch a single activity by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved activity", content = @Content(schema = @Schema(implementation = ActivityDTO.class))),
        @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(
        @Parameter(description = "UUID for unique activity identifier", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        return activityService.getActivityById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new activity with validation
    @Operation(summary = "Create a new activity", description = "Create a new activity by providing necessary fields.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created activity", content = @Content(schema = @Schema(implementation = ActivityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Custom error map", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityDTO activityDTO) {
        ActivityDTO createdActivity = activityService.createActivity(activityDTO);
        return ResponseEntity.status(201).body(createdActivity);
    }

    // Update an existing activity with validation
    @Operation(summary = "Update an existing activity", description = "Update an existing activity by providing its ID and new data.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated activity", content = @Content(schema = @Schema(implementation = ActivityDTO.class))),
        @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(
        @Parameter(description = "UUID for unique activity identifier", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id, @Valid @RequestBody ActivityDTO activityDTO) {
        return activityService.updateActivity(id, activityDTO)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an activity by ID
    @Operation(summary = "Delete an activity", description = "Delete an activity by providing its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted activity", content = @Content),
        @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable UUID id) {
        boolean isDeleted = activityService.deleteActivity(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
