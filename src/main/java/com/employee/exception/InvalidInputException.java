package com.employee.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String fieldName, String reason) {
        super(String.format("Invalid input for %s: %s", fieldName, reason));
    }
}
