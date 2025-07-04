package com.fawry.ecommerce.service;

/**
 * Interface for items that can be shipped
 * Required by ShippingService
 */
public interface ShippingItem {
    /**
     * Get the name of the item
     * @return the name of the item
     */
    String getName();
    
    /**
     * Get the weight of the item
     * @return the weight in kilograms
     */
    double getWeight();
} 