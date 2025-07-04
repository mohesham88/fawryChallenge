package com.fawry.ecommerce.model.product;

/**
 * Mobile product - non-expirable and no shipping required
 */
public class Mobile extends Product {
    private double weight;
    
    /**
     * Constructor for Mobile
     * @param name the name of the mobile
     * @param price the price of the mobile
     * @param quantity the available quantity
     * @param weight the weight in kilograms
     */
    public Mobile(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
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
        return String.format("Mobile: %s - $%.2f (Qty: %d, Weight: %.1fkg)", 
                           name, price, quantity, weight);
    }
} 