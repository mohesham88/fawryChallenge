package com.fawry.ecommerce.exception;

/**
 * Exception thrown when customer has insufficient balance
 */
public class InsufficientBalanceException extends Exception {
    
    /**
     * Constructor with message
     * @param message the exception message
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
} 