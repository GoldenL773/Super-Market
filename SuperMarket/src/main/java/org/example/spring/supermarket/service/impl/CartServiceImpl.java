package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.dto.ProductDTO;
import org.example.spring.supermarket.entity.*;
import org.example.spring.supermarket.repository.CartRepository;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.example.spring.supermarket.repository.ProductRepository;
import org.example.spring.supermarket.service.CartService;
import org.example.spring.supermarket.service.CategoryService;
import org.example.spring.supermarket.service.OrderService;
import org.example.spring.supermarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {
    private final ProductService productService;
    private final OrderService orderService;
    private CategoryService categoryService;
    // In-memory storage for shopping carts (session ID -> cart items)
    private final Map<String, Map<Integer, Integer>> carts = new ConcurrentHashMap<>();

    @Autowired
    public CartServiceImpl(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public void addToCart(String sessionId, int productId, int quantity) {
        Map<Integer, Integer> cart = carts.computeIfAbsent(sessionId, k -> new HashMap<>());

        // If the product is already in the cart, update its quantity
        cart.compute(productId, (key, existingQuantity) ->
                (existingQuantity == null) ? quantity : existingQuantity + quantity
        );
    }

    @Override
    public void removeFromCart(String sessionId, int productId) {
        Map<Integer, Integer> cart = carts.get(sessionId);
        if (cart != null) {
            cart.remove(productId);

            // Remove the cart if it's empty
            if (cart.isEmpty()) {
                carts.remove(sessionId);
            }
        }
    }

    @Override
    public void updateCartItemQuantity(String sessionId, int productId, int quantity) {
        Map<Integer, Integer> cart = carts.get(sessionId);
        if (cart != null) {
            if (quantity <= 0) {
                cart.remove(productId);

                // Remove the cart if it's empty
                if (cart.isEmpty()) {
                    carts.remove(sessionId);
                }
            } else {
                cart.put(productId, quantity);
            }
        }
    }

    @Override
    public void clearCart(String sessionId) {
        carts.remove(sessionId);
    }

    @Override
    public Map<ProductDTO, Integer> getCartItems(String sessionId) {
        Map<ProductDTO, Integer> cartItems = new HashMap<>();
        Map<Integer, Integer> cart = carts.get(sessionId);

        if (cart != null) {
            cart.forEach((productId, quantity) -> {
                productService.getProductById(productId).ifPresent(product -> {
                    cartItems.put(product, quantity);
                });
            });
        }

        return cartItems;
    }

    @Override
    public int getCartSize(String sessionId) {
        Map<Integer, Integer> cart = carts.get(sessionId);
        return (cart != null) ? cart.size() : 0;
    }

    @Override
    public double getCartTotal(String sessionId) {
        double total = 0.0;
        Map<ProductDTO, Integer> cartItems = getCartItems(sessionId);

        for (Map.Entry<ProductDTO, Integer> entry : cartItems.entrySet()) {
            total += (Double.parseDouble(entry.getKey().getPrice().toString()) * entry.getValue());
        }

        return total;
    }

    @Override
    public Order checkout(String sessionId, Customer customer) {
        Map<ProductDTO, Integer> cartItems = getCartItems(sessionId);

        if (cartItems.isEmpty()) {
            return null;
        }

        // Create a new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("Pending");

        // Create order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Map.Entry<ProductDTO, Integer> entry : cartItems.entrySet()) {
            ProductDTO product = entry.getKey();
            Product p = new Product();
            p.setId(product.getId());
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setCategory(categoryService.getCategories(product.getCategoryId()));
            p.setDescription(product.getDescription());
            p.setImage(product.getImage());
            p.setCreatedAt(product.getCreatedAt());
            int quantity = entry.getValue();
            OrderDetail detail = new OrderDetail();
            detail.setProduct(p);
            detail.setQuantity(quantity);
            detail.setPrice(product.getPrice());

            orderDetails.add(detail);
        }
        // Create the order with order details
        Order savedOrder = orderService.createOrder(order, orderDetails);
        // Clear the cart after successful checkout
        clearCart(sessionId);
        return savedOrder;
    }

}
