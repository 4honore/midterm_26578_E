package com.medilink.medilink.HMS.exception;

/**
 * DuplicateResourceException - Thrown when trying to create a resource that already exists
 * 
 * This exception should result in HTTP 400 Bad Request status code
 */
public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}