package com.fawry.ecommerce.model.product;

import java.time.LocalDate;

/**
 * Cheese product - expirable and shippable
 */
public class Cheese extends ExpirableProduct implements Shippable {
    private double weight;
    
    /**
     * Constructor for Cheese
     * @param name the name of the cheese
     * @param price the price of the cheese
     * @param quantity the available quantity
     * @param expirationDate the expiration date
     * @param weight the weight in kilograms
     */
    public Cheese(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity, expirationDate);
        this.weight = weight;
    }
    
    @Override
    public double getWeight() {
        return weight;
    }
    
    @Override
    public boolean requiresShipping() {
        return true;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return String.format("Cheese: %s - $%.2f (Qty: %d, Expires: %s, Weight: %.1fkg)", 
                           name, price, quantity, expirationDate, weight);
    }
} 