package com.fawry.ecommerce.model.product;

/**
 * ScratchCard product - non-expirable and no shipping required
 */
public class ScratchCard extends Product {
    private static final double DEFAULT_WEIGHT = 0.001; // 1 gram
    
    /**
     * Constructor for ScratchCard
     * @param name the name of the scratch card
     * @param price the price of the scratch card
     * @param quantity the available quantity
     */
    public ScratchCard(String name, double price, int quantity) {
        super(name, price, quantity);
    }
    
    @Override
    public double getWeight() {
        return DEFAULT_WEIGHT;
    }
    
    @Override
    public String toString() {
        return String.format("ScratchCard: %s - $%.2f (Qty: %d)", 
                           name, price, quantity);
    }
} 