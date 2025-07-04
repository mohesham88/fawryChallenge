package com.fawry.ecommerce.model.product;

import java.time.LocalDate;

/**
 * Abstract class for products that can expire
 */
public abstract class ExpirableProduct extends Product implements Expirable {
    protected LocalDate expirationDate;
    
    /**
     * Constructor for ExpirableProduct
     * @param name the name of the product
     * @param price the price of the product
     * @param quantity the available quantity
     * @param expirationDate the expiration date
     */
    public ExpirableProduct(String name, double price, int quantity, LocalDate expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }
    
    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    
    @Override
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
    
    /**
     * Check if the product is available and not expired
     * @param requestedQuantity the requested quantity
     * @return true if available and not expired, false otherwise
     */
    @Override
    public boolean isAvailable(int requestedQuantity) {
        return super.isAvailable(requestedQuantity) && !isExpired();
    }
    
    @Override
    public String toString() {
        return String.format("%s - $%.2f (Qty: %d, Expires: %s)", 
                           name, price, quantity, expirationDate);
    }
} 