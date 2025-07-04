package com.fawry.ecommerce.model.product;

import com.fawry.ecommerce.service.ShippingItem;

/**
 * Abstract base class for all products in the e-commerce system
 */
public abstract class Product implements ShippingItem {
    protected String name;
    protected double price;
    protected int quantity;
    
    /**
     * Constructor for Product
     * @param name the name of the product
     * @param price the price of the product
     * @param quantity the available quantity
     */
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Reduce the quantity of the product
     * @param amount the amount to reduce
     * @return true if successful, false if insufficient quantity
     */
    public boolean reduceQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }
    
    /**
     * Check if the product is available in the requested quantity
     * @param requestedQuantity the requested quantity
     * @return true if available, false otherwise
     */
    public boolean isAvailable(int requestedQuantity) {
        return quantity >= requestedQuantity;
    }
    
    /**
     * Check if the product is out of stock
     * @return true if out of stock, false otherwise
     */
    public boolean isOutOfStock() {
        return quantity <= 0;
    }
    
    @Override
    public String toString() {
        return String.format("%s - $%.2f (Qty: %d)", name, price, quantity);
    }
} 