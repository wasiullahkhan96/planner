package com.example.planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

    @Schema(description = "ID of the activity", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the activity", example = "Yoga Class")
    private String name;

    @NotNull(message = "Location cannot be null")
    @Schema(description = "Location of the activity", example = "City Park")
    private String location;

    @NotNull(message = "Date cannot be null")
    @Schema(description = "Date of the activity", example = "2024-09-15")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null")
    @Schema(description = "Start time of the activity", example = "10:00")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    @Schema(description = "End time of the activity", example = "12:00")
    private LocalTime endTime;

    @PositiveOrZero(message = "Total seats must be zero or a positive number")
    @Schema(description = "Total number of seats available for the activity", example = "20")
    private int totalSeats;

    @PositiveOrZero(message = "Occupied seats must be zero or a positive number")
    @Schema(description = "Number of seats that are occupied", example = "5")
    private int occupiedSeats;
}
