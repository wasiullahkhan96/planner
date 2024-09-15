package com.example.planner.exceptions;

public class SeatLimitExceededException extends RuntimeException {
    public SeatLimitExceededException(String message) {
        super(message);
    }
}