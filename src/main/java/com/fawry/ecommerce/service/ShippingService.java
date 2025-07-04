package com.fawry.ecommerce.service;

import java.util.List;

/**
 * Service class for handling shipping operations
 * Implements Singleton Pattern to ensure only one shipping service instance
 */
public class ShippingService {
    private static volatile ShippingService instance;
    private static final double SHIPPING_RATE_PER_KG = 25.0; // $25 per kg
    private static final double BASE_SHIPPING_FEE = 5.0; // Base fee of $5
    
    /**
     * Private constructor for ShippingService (Singleton Pattern)
     */
    private ShippingService() {
        // Private constructor to prevent direct instantiation
    }
    
    /**
     * Get the singleton instance of ShippingService
     * Thread-safe implementation using double-checked locking
     * @return the singleton ShippingService instance
     */
    public static ShippingService getInstance() {
        if (instance == null) {
            synchronized (ShippingService.class) {
                if (instance == null) {
                    instance = new ShippingService();
                }
            }
        }
        return instance;
    }
    
    /**
     * Calculate shipping fee based on items
     * @param items list of shippable items
     * @return the total shipping fee
     */
    public double calculateShippingFee(List<ShippingItem> items) {
        if (items.isEmpty()) {
            return 0.0;
        }
        
        double totalWeight = items.stream()
                .mapToDouble(ShippingItem::getWeight)
                .sum();
        
        return BASE_SHIPPING_FEE + (totalWeight * SHIPPING_RATE_PER_KG);
    }
    
    /**
     * Process shipment and print shipment notice
     * @param items list of shippable items with quantities
     */
    public void processShipment(List<ShippableCartItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items to ship");
            return;
        }
        
        System.out.println("** Shipment notice **");
        
        double totalWeight = 0.0;
        for (ShippableCartItem item : items) {
            double itemWeight = item.getWeight() * item.getQuantity();
            totalWeight += itemWeight;
            
            System.out.printf("%dx %s %.0fg%n", 
                item.getQuantity(), 
                item.getName(), 
                item.getWeight() * 1000); // Convert to grams for display
        }
        
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
    
    /**
     * Inner class to represent shippable cart items with quantity
     */
    public static class ShippableCartItem implements ShippingItem {
        private final ShippingItem item;
        private final int quantity;
        
        public ShippableCartItem(ShippingItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }
        
        @Override
        public String getName() {
            return item.getName();
        }
        
        @Override
        public double getWeight() {
            return item.getWeight();
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public double getTotalWeight() {
            return item.getWeight() * quantity;
        }
    }
} 