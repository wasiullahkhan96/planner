package com.example.planner.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.planner.DTOs.ActivityDTO;
import com.example.planner.services.ActivityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    // Get all activities without pagination
    @Operation(summary = "Get all activities", description = "Fetch all activities without pagination.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved activities", content = @Content(schema = @Schema(implementation = List.class))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    // Get a specific activity by ID
    @Operation(summary = "Get activity by ID", description = "Fetch a single activity by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved activity", content = @Content(schema = @Schema(implementation = ActivityDTO.class))),
        @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
                              .map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new activity with validation
    @Operation(summary = "Create a new activity", description = "Create a new activity by providing necessary fields.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created activity", content = @Content(schema = @Schema(implementation = ActivityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
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
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivityDTO activityDTO) {
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
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        boolean isDeleted = activityService.deleteActivity(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
