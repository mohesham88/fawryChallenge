package com.fawry.ecommerce.model.product;

/**
 * TV product - non-expirable but requires shipping
 */
public class TV extends Product implements Shippable {
    private double weight;
    
    /**
     * Constructor for TV
     * @param name the name of the TV
     * @param price the price of the TV
     * @param quantity the available quantity
     * @param weight the weight in kilograms
     */
    public TV(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
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
        return String.format("TV: %s - $%.2f (Qty: %d, Weight: %.1fkg)", 
                           name, price, quantity, weight);
    }
} 