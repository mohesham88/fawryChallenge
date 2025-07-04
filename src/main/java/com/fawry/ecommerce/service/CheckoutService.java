package com.fawry.ecommerce.service;

import com.fawry.ecommerce.model.Cart;
import com.fawry.ecommerce.model.CartItem;
import com.fawry.ecommerce.model.customer.Customer;
import com.fawry.ecommerce.model.product.Product;
import com.fawry.ecommerce.model.product.Shippable;
import com.fawry.ecommerce.exception.InsufficientBalanceException;
import com.fawry.ecommerce.exception.InsufficientStockException;
import com.fawry.ecommerce.exception.ProductExpiredException;
import com.fawry.ecommerce.service.ShippingService.ShippableCartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling checkout operations
 * Implements Singleton Pattern to ensure only one checkout service instance
 */
public class CheckoutService {
    private static volatile CheckoutService instance;
    private ShippingService shippingService;
    
    /**
     * Private constructor for CheckoutService (Singleton Pattern)
     */
    private CheckoutService() {
        this.shippingService = ShippingService.getInstance();
    }
    
    /**
     * Get the singleton instance of CheckoutService
     * Thread-safe implementation using double-checked locking
     * @return the singleton CheckoutService instance
     */
    public static CheckoutService getInstance() {
        if (instance == null) {
            synchronized (CheckoutService.class) {
                if (instance == null) {
                    instance = new CheckoutService();
                }
            }
        }
        return instance;
    }
    
    /**
     * Process checkout for a customer's cart
     * @param customer the customer
     * @param cart the shopping cart
     * @throws InsufficientBalanceException if customer has insufficient balance
     * @throws InsufficientStockException if any item is out of stock
     * @throws ProductExpiredException if any item is expired
     * @throws IllegalArgumentException if cart is empty
     */
    public void checkout(Customer customer, Cart cart) 
            throws InsufficientBalanceException, InsufficientStockException, 
                   ProductExpiredException, IllegalStateException {
        
        // Check if cart is empty
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        
        // Validate all items before processing
        validateCartItems(cart);
        
        // Calculate totals
        double subtotal = cart.getSubtotal();
        List<ShippableCartItem> shippableItems = getShippableItems(cart);
        double shippingFee = shippingService.calculateShippingFee(
            shippableItems.stream()
                .map(item -> (ShippingItem) item)
                .collect(java.util.stream.Collectors.toList())
        );
        double totalAmount = subtotal + shippingFee;
        
        // Check customer balance
        if (!customer.hasSufficientBalance(totalAmount)) {
            throw new InsufficientBalanceException(
                String.format("Insufficient balance. Required: $%.2f, Available: $%.2f", 
                            totalAmount, customer.getBalance()));
        }
        
        // Process payment
        customer.deductBalance(totalAmount);
        
        // Reduce product quantities
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
        
        // Process shipment if there are shippable items
        if (!shippableItems.isEmpty()) {
            shippingService.processShipment(shippableItems);
        }
        
        // Print checkout receipt
        printCheckoutReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());
        
        // Clear the cart
        cart.clear();
    }
    
    /**
     * Validate all items in the cart
     * @param cart the cart to validate
     * @throws InsufficientStockException if any item is out of stock
     * @throws ProductExpiredException if any item is expired
     */
    private void validateCartItems(Cart cart) 
            throws InsufficientStockException, ProductExpiredException {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            
            // Check stock availability
            if (!product.isAvailable(item.getQuantity())) {
                throw new InsufficientStockException(
                    String.format("Product %s is not available in requested quantity. Available: %d, Requested: %d",
                                product.getName(), product.getQuantity(), item.getQuantity()));
            }
            
            // Check if product is expired
            if (product instanceof com.fawry.ecommerce.model.product.ExpirableProduct) {
                com.fawry.ecommerce.model.product.ExpirableProduct expirable = 
                    (com.fawry.ecommerce.model.product.ExpirableProduct) product;
                if (expirable.isExpired()) {
                    throw new ProductExpiredException("Product " + product.getName() + " is expired");
                }
            }
        }
    }
    
    /**
     * Get shippable items from the cart
     * @param cart the cart
     * @return list of shippable cart items
     */
    private List<ShippableCartItem> getShippableItems(Cart cart) {
        List<ShippableCartItem> shippableItems = new ArrayList<>();
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product instanceof Shippable) {
                Shippable shippableProduct = (Shippable) product;
                if (shippableProduct.requiresShipping()) {
                    shippableItems.add(new ShippableCartItem(product, item.getQuantity()));
                }
            }
        }
        
        return shippableItems;
    }
    
    /**
     * Print the checkout receipt
     * @param cart the cart
     * @param subtotal the subtotal
     * @param shippingFee the shipping fee
     * @param totalAmount the total amount
     * @param remainingBalance the customer's remaining balance
     */
    private void printCheckoutReceipt(Cart cart, double subtotal, double shippingFee, 
                                    double totalAmount, double remainingBalance) {
        System.out.println("** Checkout receipt **");
        
        // Print items
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n", 
                item.getQuantity(), 
                item.getProduct().getName(), 
                item.getTotalPrice());
        }
        
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", totalAmount);
        System.out.printf("Customer balance after payment: $%.2f%n", remainingBalance);
    }
} 