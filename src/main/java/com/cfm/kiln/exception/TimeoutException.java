package com.cfm.kiln.exception;

public class TimeoutException extends RuntimeException{
    public TimeoutException(String message) {
        super(message);
    }
}
