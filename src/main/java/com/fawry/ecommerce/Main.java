package com.fawry.ecommerce;

import com.fawry.ecommerce.model.Cart;
import com.fawry.ecommerce.model.customer.Customer;
import com.fawry.ecommerce.model.product.*;
import com.fawry.ecommerce.factory.ProductFactory;
import com.fawry.ecommerce.service.CheckoutService;
import com.fawry.ecommerce.service.ShippingService;
import com.fawry.ecommerce.exception.*;

import java.time.LocalDate;

/**
 * Main class demonstrating the e-commerce system functionality
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Fawry E-Commerce System Demo ===\n");
        
        // Create products using Factory Pattern
        Cheese cheese = ProductFactory.createCheese("Cheddar Cheese", 100.0, 10, LocalDate.now().plusDays(7), 0.2);
        Biscuits biscuits = ProductFactory.createBiscuits("Oreo Biscuits", 75.0, 15, LocalDate.now().plusDays(30), 0.35);
        TV tv = ProductFactory.createTV("Samsung Smart TV", 800.0, 5, 15.0);
        Mobile mobile = ProductFactory.createMobile("iPhone 14", 1200.0, 8, 0.2);
        ScratchCard scratchCard = ProductFactory.createScratchCard("Mobile Credit Card", 50.0, 20);
        

        // Create customers
        Customer customer1 = new Customer("Mohamed Hesham", 2000.0);
        Customer customer2 = new Customer("Abdelrahman Hesham", 100.0);
        
        // Get singleton service instances
        CheckoutService checkoutService = CheckoutService.getInstance();

        // Demo 1: Successful checkout with mixed products
        System.out.println("=== Demo 1: Successful Checkout ===");
        demonstrateSuccessfulCheckout(customer1, cheese, biscuits, tv, scratchCard, checkoutService);
        
        System.out.println("\n=== Demo 2: Error Cases ===");
        // Demo 2: Error cases
        demonstrateErrorCases(customer2, cheese, tv, checkoutService);
        
        System.out.println("\n=== Demo 3: Edge Cases ===");
        // Demo 3: Edge cases
        demonstrateEdgeCases(customer1, cheese, biscuits, mobile, checkoutService);
        
        System.out.println("\n=== Demo 4: Product Expiration ===");
        // Demo 4: Expired products
        demonstrateExpiredProducts(customer1, checkoutService);
    }
    
    /**
     * Demonstrate successful checkout scenario
     */
    private static void demonstrateSuccessfulCheckout(Customer customer, Cheese cheese, 
                                                    Biscuits biscuits, TV tv, ScratchCard scratchCard, 
                                                    CheckoutService checkoutService) {
        try {
            Cart cart = new Cart();
            
            System.out.println(customer);
            System.out.println("Adding products to cart...");
            
            // Add products to cart
            cart.addProduct(cheese, 2);
            cart.addProduct(biscuits, 1);
            cart.addProduct(tv, 1);
            cart.addProduct(scratchCard, 1);
            
            System.out.println(cart);
            
            System.out.println("\nProcessing checkout...");
            checkoutService.checkout(customer, cart);
            
            System.out.println("\nCheckout completed successfully!");
            System.out.println("Updated  " + customer);
            
        } catch (Exception e) {
            System.out.println("Error during checkout: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate error cases
     */
    private static void demonstrateErrorCases(Customer customer, Cheese cheese, 
                                            TV tv, CheckoutService checkoutService) {
        
        // Test 1: Empty cart
        System.out.println("Test 1: Empty Cart");
        try {
            Cart emptyCart = new Cart();
            checkoutService.checkout(customer, emptyCart);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }
        
        // Test 2: Insufficient balance
        System.out.println("\nTest 2: Insufficient Balance");
        try {
            Cart cart = new Cart();
            cart.addProduct(tv, 1); // TV costs $800, customer has $100
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }
        
        // Test 3: Out of stock
        System.out.println("\nTest 3: Out of Stock");
        try {
            Cart cart = new Cart();
            cart.addProduct(cheese, 50); // Requesting more than available
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate edge cases
     */
    private static void demonstrateEdgeCases(Customer customer, Cheese cheese, 
                                           Biscuits biscuits, Mobile mobile, 
                                           CheckoutService checkoutService) {
        
        // Test 1: Non-shippable items only
        System.out.println("Test 1: Non-shippable items only (Mobile + Scratch Card)");
        try {
            Cart cart = new Cart();
            ScratchCard card = ProductFactory.createScratchCard("Google Play Card", 25.0, 10);
            cart.addProduct(mobile, 1);
            cart.addProduct(card, 2);
            
            System.out.println("Cart contents: " + cart.getTotalItemCount() + " items");
            checkoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Test 2: Adding same product multiple times
        System.out.println("\nTest 2: Adding same product multiple times");
        try {
            Cart cart = new Cart();
            cart.addProduct(cheese, 1);
            cart.addProduct(cheese, 2); // Should merge with existing
            
            System.out.println("Total cheese in cart: " + 
                cart.getItems().stream()
                    .filter(item -> item.getProduct().equals(cheese))
                    .findFirst()
                    .map(item -> item.getQuantity())
                    .orElse(0) + " units");
                    
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate expired product handling
     */
    private static void demonstrateExpiredProducts(Customer customer, CheckoutService checkoutService) {
        try {
            // Create expired cheese using Factory Pattern
            Cheese expiredCheese = ProductFactory.createCheese("Expired Cheese", 50.0, 5, 
                                            LocalDate.now().minusDays(1), 0.3);
            
            Cart cart = new Cart();
            cart.addProduct(expiredCheese, 1);
            
            System.out.println("Attempting to checkout with expired cheese...");
            checkoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }

}