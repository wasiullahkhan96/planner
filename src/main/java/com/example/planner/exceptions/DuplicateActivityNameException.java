package com.example.planner.exceptions;

public class DuplicateActivityNameException extends RuntimeException {
    public DuplicateActivityNameException(String message) {
        super(message);
    }
}