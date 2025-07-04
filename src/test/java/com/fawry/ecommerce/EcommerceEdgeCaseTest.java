package com.fawry.ecommerce;

import com.fawry.ecommerce.exception.*;
import com.fawry.ecommerce.factory.ProductFactory;
import com.fawry.ecommerce.model.Cart;
import com.fawry.ecommerce.model.customer.Customer;
import com.fawry.ecommerce.model.product.*;
import com.fawry.ecommerce.service.CheckoutService;
import com.fawry.ecommerce.service.ShippingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Edge & corner‑case coverage for the Fawry e‑commerce kata.
 *
 * Aims to hit:
 *   • Empty cart
 *   • Insufficient balance
 *   • Out‑of‑stock
 *   • Expired product
 *   • Non‑shippable‑only order (shipping fee = 0, notice not sent)
 *   • Exactly‑stock boundary
 *   • Merging same product
 *   • Quantity/price/weight validation guards (negative, zero)
 *   • Balance exactly equals due amount
 */
class EcommerceEdgeCaseTest {

    private CheckoutService checkout;
    private ShippingService shipping;

    @BeforeEach
    void setUp() {
        checkout  = CheckoutService.getInstance();
        shipping  = ShippingService.getInstance();
    }

    // ---------- 1. Cart‑level corner cases ----------

    @Nested class CartEdgeCases {

        @Test
        void empty_cart_rejected() {
            Cart cart = new Cart();
            Customer c = new Customer("Mo", 500);

            assertThrows(IllegalStateException.class,
                    () -> checkout.checkout(c, cart),
                    "Empty cart should trigger exception");
        }

        @Test
        void adding_negative_quantity_should_fail() {
            Cart cart = new Cart();
            Cheese cheese = ProductFactory.createCheese("Cheddar", 100, 10,
                    LocalDate.now().plusDays(5), 0.2);

            assertThrows(IllegalArgumentException.class,
                    () -> cart.addProduct(cheese, -1));
        }

        @Test
        void adding_zero_quantity_should_fail() {
            Cart cart = new Cart();
            Cheese cheese = ProductFactory.createCheese("Cheddar", 100, 10,
                    LocalDate.now().plusDays(5), 0.2);

            assertThrows(IllegalArgumentException.class,
                    () -> cart.addProduct(cheese, 0));
        }

        @Test
        void adding_same_product_merges_quantities() throws InsufficientStockException, ProductExpiredException {
            Cart cart = new Cart();
            Cheese cheese = ProductFactory.createCheese("Cheddar", 100, 10,
                    LocalDate.now().plusDays(5), 0.2);

            cart.addProduct(cheese, 3);
            cart.addProduct(cheese, 2);

            assertEquals(5,
                    cart.getItems().stream()
                            .filter(i -> i.getProduct().equals(cheese))
                            .findFirst().orElseThrow().getQuantity());
        }
    }

    // ---------- 2. Inventory / stock edge cases ----------

    @Test
    void requesting_more_than_stock_throws() throws InsufficientStockException, ProductExpiredException {
        Cheese cheese = ProductFactory.createCheese("Cheddar", 100, 3,
                LocalDate.now().plusDays(5), 0.2);
        Cart cart = new Cart();
        cart.addProduct(cheese, 3);      // OK

        assertThrows(InsufficientStockException.class,
                () -> cart.addProduct(cheese, 1));  // pushes over stock
    }

    @Test
    void requesting_exact_stock_is_allowed() throws InsufficientStockException, ProductExpiredException {
        Cheese cheese = ProductFactory.createCheese("Cheddar", 100, 3,
                LocalDate.now().plusDays(5), 0.2);
        Cart cart = new Cart();
        cart.addProduct(cheese, 3);

        Customer c = new Customer("Exact Stock Buyer", 500);
        assertDoesNotThrow(() -> checkout.checkout(c, cart));
    }

    // ---------- 3. Balance‑related edge cases ----------

    @Test
    void insufficient_balance_throws() throws InsufficientStockException, ProductExpiredException {
        TV tv = ProductFactory.createTV("TV", 800, 1, 10);
        Customer poor = new Customer("Poor Bob", 100);

        Cart cart = new Cart();
        cart.addProduct(tv, 1);

        assertThrows(InsufficientBalanceException.class,
                () -> checkout.checkout(poor, cart));
    }

    @Test
    void balance_exactly_equals_total_is_ok() throws InsufficientStockException, ProductExpiredException, InsufficientBalanceException {
        TV tv = ProductFactory.createTV("TV", 800, 1, 10);

        // 25 for every kg and 5 base
        double shipping_cost = 10 * 25 + 5;
        Customer c = new Customer("Exact Balance", 800 + shipping_cost); // 800 + 30 flat shipping

        Cart cart = new Cart();
        cart.addProduct(tv, 1);

        checkout.checkout(c, cart);
        assertEquals(0, c.getBalance(), 0.01);
    }

    // ---------- 4. Expiry edge cases ----------

    @Test
    void expired_product_cannot_be_added() {
        Cheese expired = ProductFactory.createCheese("Old", 50, 5,
                LocalDate.now().minusDays(1), 0.2);
        Cart cart = new Cart();

        assertThrows(ProductExpiredException.class,
                () -> cart.addProduct(expired, 1));
    }

    @Test
    void product_expiring_today_is_still_valid() throws InsufficientStockException, ProductExpiredException {
        Cheese todayExpiry = ProductFactory.createCheese("Fresh", 50, 5,
                LocalDate.now(), 0.2);
        Cart cart = new Cart();
        cart.addProduct(todayExpiry, 1);

        Customer c = new Customer("Today Buyer", 200);
        assertDoesNotThrow(() -> checkout.checkout(c, cart));
    }

    // ---------- 5. Shipping edge cases ----------

    @Test
    void non_shippable_only_order_produces_zero_shipping_fee() throws InsufficientStockException, ProductExpiredException, InsufficientBalanceException {
        Mobile phone = ProductFactory.createMobile("Phone", 500, 1, 0.2);
        ScratchCard card = ProductFactory.createScratchCard("Card", 50, 2);

        Cart cart = new Cart();
        cart.addProduct(phone, 1);
        cart.addProduct(card, 1);

        Customer c = new Customer("Digital Dan", 1000);

        double before = c.getBalance();
        checkout.checkout(c, cart);
        double expectedTotal = phone.getPrice() + card.getPrice(); // no shipping fee

        assertEquals(before - expectedTotal, c.getBalance(), 0.01);
    }


}
