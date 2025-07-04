package com.fawry.ecommerce.model.product;

import java.time.LocalDate;

/**
 * Biscuits product - expirable but no shipping required
 */
public class Biscuits extends ExpirableProduct {
    private double weight;
    
    /**
     * Constructor for Biscuits
     * @param name the name of the biscuits
     * @param price the price of the biscuits
     * @param quantity the available quantity
     * @param expirationDate the expiration date
     * @param weight the weight in kilograms
     */
    public Biscuits(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity, expirationDate);
        this.weight = weight;
    }
    
    @Override
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return String.format("Biscuits: %s - $%.2f (Qty: %d, Expires: %s, Weight: %.1fkg)", 
                           name, price, quantity, expirationDate, weight);
    }
} 