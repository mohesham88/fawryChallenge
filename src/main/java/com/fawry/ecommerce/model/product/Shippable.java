package com.fawry.ecommerce.model.product;

/**
 * Interface for products that require shipping
 */
public interface Shippable {
    /**
     * Get the weight of the product for shipping calculations
     * @return the weight in kilograms
     */
    double getWeight();
    
    /**
     * Check if the product requires shipping
     * @return true if the product requires shipping, false otherwise
     */
    boolean requiresShipping();
} 