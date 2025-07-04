package com.fawry.ecommerce.model.customer;

/**
 * Customer class representing a customer in the e-commerce system
 */
public class Customer {
    private String name;
    private double balance;
    
    /**
     * Constructor for Customer
     * @param name the name of the customer
     * @param balance the initial balance of the customer
     */
    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    /**
     * Deduct amount from customer's balance
     * @param amount the amount to deduct
     * @return true if successful, false if insufficient balance
     */
    public boolean deductBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    /**
     * Add amount to customer's balance
     * @param amount the amount to add
     */
    public void addBalance(double amount) {
        balance += amount;
    }
    
    /**
     * Check if customer has sufficient balance
     * @param amount the amount to check
     * @return true if sufficient balance, false otherwise
     */
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }
    
    @Override
    public String toString() {
        return String.format("Customer: %s (Balance: $%.2f)", name, balance);
    }
} 