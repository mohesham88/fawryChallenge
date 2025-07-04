package com.fawry.ecommerce.factory;

import com.fawry.ecommerce.model.product.*;
import java.time.LocalDate;

/**
 * Factory class for creating different types of products
 * Implements the Factory Design Pattern with specific creation methods
 */
public class ProductFactory {
    
    /**
     * Create a cheese product with specific parameters
     * @param name the name of the cheese
     * @param price the price
     * @param quantity the quantity
     * @param expirationDate the expiration date
     * @param weight the weight in kg
     * @return the created cheese product
     */
    public static Cheese createCheese(String name, double price, int quantity, 
                                    LocalDate expirationDate, double weight) {
        return new Cheese(name, price, quantity, expirationDate, weight);
    }
    
    /**
     * Create a biscuits product with specific parameters
     * @param name the name of the biscuits
     * @param price the price
     * @param quantity the quantity
     * @param expirationDate the expiration date
     * @param weight the weight in kg
     * @return the created biscuits product
     */
    public static Biscuits createBiscuits(String name, double price, int quantity, 
                                        LocalDate expirationDate, double weight) {
        return new Biscuits(name, price, quantity, expirationDate, weight);
    }
    
    /**
     * Create a TV product with specific parameters
     * @param name the name of the TV
     * @param price the price
     * @param quantity the quantity
     * @param weight the weight in kg
     * @return the created TV product
     */
    public static TV createTV(String name, double price, int quantity, double weight) {
        return new TV(name, price, quantity, weight);
    }
    
    /**
     * Create a mobile product with specific parameters
     * @param name the name of the mobile
     * @param price the price
     * @param quantity the quantity
     * @param weight the weight in kg
     * @return the created mobile product
     */
    public static Mobile createMobile(String name, double price, int quantity, double weight) {
        return new Mobile(name, price, quantity, weight);
    }
    
    /**
     * Create a scratch card product
     * @param name the name of the scratch card
     * @param price the price
     * @param quantity the quantity
     * @return the created scratch card product
     */
    public static ScratchCard createScratchCard(String name, double price, int quantity) {
        return new ScratchCard(name, price, quantity);
    }
    
    /**
     * Create demo products with predefined values for testing
     */
    public static class Demo {
        
        /**
         * Create a demo cheese product
         * @return demo cheese with default values
         */
        public static Cheese createCheese() {
            return ProductFactory.createCheese("Demo Cheddar Cheese", 100.0, 10, 
                                             LocalDate.now().plusDays(7), 0.2);
        }
        
        /**
         * Create a demo biscuits product
         * @return demo biscuits with default values
         */
        public static Biscuits createBiscuits() {
            return ProductFactory.createBiscuits("Demo Oreo Biscuits", 75.0, 15, 
                                                LocalDate.now().plusDays(30), 0.35);
        }
        
        /**
         * Create a demo TV product
         * @return demo TV with default values
         */
        public static TV createTV() {
            return ProductFactory.createTV("Demo Samsung Smart TV", 800.0, 5, 15.0);
        }
        
        /**
         * Create a demo mobile product
         * @return demo mobile with default values
         */
        public static Mobile createMobile() {
            return ProductFactory.createMobile("Demo iPhone 14", 1200.0, 8, 0.2);
        }
        
        /**
         * Create a demo scratch card product
         * @return demo scratch card with default values
         */
        public static ScratchCard createScratchCard() {
            return ProductFactory.createScratchCard("Demo Mobile Credit Card", 50.0, 20);
        }
    }
} 