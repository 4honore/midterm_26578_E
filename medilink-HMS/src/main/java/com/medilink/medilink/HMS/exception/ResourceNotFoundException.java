package com.medilink.medilink.HMS.exception;

/**
 * ResourceNotFoundException - Thrown when a requested resource is not found
 * 
 * This exception should result in HTTP 404 Not Found status code
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}