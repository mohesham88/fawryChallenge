package com.fawry.ecommerce.exception;

/**
 * Exception thrown when a product is expired
 */
public class ProductExpiredException extends Exception {
    
    /**
     * Constructor with message
     * @param message the exception message
     */
    public ProductExpiredException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public ProductExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
} 