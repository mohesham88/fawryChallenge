package com.fawry.ecommerce.model;

import com.fawry.ecommerce.model.product.Product;
import com.fawry.ecommerce.exception.InsufficientStockException;
import com.fawry.ecommerce.exception.ProductExpiredException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cart class representing a shopping cart
 */
public class Cart {
    private List<CartItem> items;
    
    /**
     * Constructor for Cart
     */
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    /**
     * Add a product to the cart
     * @param product the product to add
     * @param quantity the quantity to add
     * @throws InsufficientStockException if not enough stock available
     * @throws ProductExpiredException if the product is expired
     */
    public void addProduct(Product product, int quantity) 
            throws InsufficientStockException, ProductExpiredException {

        // quantity must be greater than 0
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Check if product already exists in cart
        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();
        int existingQuantity = 0;
        if(existingItem.isPresent()) {existingQuantity = existingItem.get().getQuantity();}



        // Check if product is available
        if (!product.isAvailable(quantity + existingQuantity)) {
            if (product.isOutOfStock()) {
                throw new InsufficientStockException("Product " + product.getName() + " is out of stock");
            }else if(quantity + existingQuantity > product.getQuantity()) {
                throw new InsufficientStockException("Product " + product.getName() + " has only " + quantity + " available");
            }
            else {
                // it might be due to the product being expired
                // check if product is expired
                if (product instanceof com.fawry.ecommerce.model.product.ExpirableProduct) {
                    com.fawry.ecommerce.model.product.ExpirableProduct expirable =
                            (com.fawry.ecommerce.model.product.ExpirableProduct) product;
                    if (expirable.isExpired()) {
                        throw new ProductExpiredException("Product " + product.getName() + " is expired");
                    }
                }
            }
        }

        if (existingItem.isPresent()) {
            existingItem.get().addQuantity(quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }
    
    /**
     * Remove a product from the cart
     * @param product the product to remove
     */
    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }
    
    /**
     * Clear all items from the cart
     */
    public void clear() {
        items.clear();
    }
    
    /**
     * Check if the cart is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Get all items in the cart
     * @return list of cart items
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }
    
    /**
     * Calculate the subtotal of all items in the cart
     * @return the subtotal
     */
    public double getSubtotal() {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
    
    /**
     * Get the total number of items in the cart
     * @return the total item count
     */
    public int getTotalItemCount() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Cart is empty";
        }
        StringBuilder sb = new StringBuilder("Cart Contents:\n");
        for (CartItem item : items) {
            sb.append("- ").append(item.toString()).append(" ($")
              .append(String.format("%.2f", item.getTotalPrice())).append(")\n");
        }
        sb.append("Subtotal: $").append(String.format("%.2f", getSubtotal()));
        return sb.toString();
    }
} 