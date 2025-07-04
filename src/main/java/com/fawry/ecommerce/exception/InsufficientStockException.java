package com.fawry.ecommerce.exception;

/**
 * Exception thrown when there is insufficient stock for a product
 */
public class InsufficientStockException extends Exception {
    
    /**
     * Constructor with message
     * @param message the exception message
     */
    public InsufficientStockException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
} 