package com.cfm.kiln.exception;

public class DHT22DataException extends RuntimeException{
    public DHT22DataException(String message) {
        super(message);
    }

    public DHT22DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
