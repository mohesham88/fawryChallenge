package com.fawry.ecommerce.model;

import com.fawry.ecommerce.model.product.Product;

/**
 * CartItem class representing an item in the shopping cart
 */
public class CartItem {
    private Product product;
    private int quantity;
    
    /**
     * Constructor for CartItem
     * @param product the product
     * @param quantity the quantity in the cart
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Get the total price for this cart item
     * @return the total price (product price * quantity)
     */
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
    
    /**
     * Add more quantity to this cart item
     * @param additionalQuantity the additional quantity to add
     */
    public void addQuantity(int additionalQuantity) {
        this.quantity += additionalQuantity;
    }
    
    @Override
    public String toString() {
        return String.format("%dx %s", quantity, product.getName());
    }
} 