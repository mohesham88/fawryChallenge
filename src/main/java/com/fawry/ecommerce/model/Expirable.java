package com.fawry.ecommerce.model;

import java.time.LocalDate;

/**
 * Interface for products that can expire
 */
public interface Expirable {
    /**
     * Get the expiration date of the product
     * @return the expiration date
     */
    LocalDate getExpirationDate();
    
    /**
     * Check if the product is expired
     * @return true if the product is expired, false otherwise
     */
    boolean isExpired();
    
    /**
     * Set the expiration date of the product
     * @param expirationDate the expiration date to set
     */
    void setExpirationDate(LocalDate expirationDate);
} 