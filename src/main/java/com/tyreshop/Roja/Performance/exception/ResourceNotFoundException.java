package com.tyreshop.Roja.Performance.exception;

/**
 * ResourceNotFoundException - Custom exception thrown when a resource is not found
 * 
 * @author Roja Performance
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor with message
     * @param message - Exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message - Exception message
     * @param cause - Root cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
