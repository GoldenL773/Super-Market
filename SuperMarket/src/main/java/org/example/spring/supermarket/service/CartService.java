package org.example.spring.supermarket.service;


import org.example.spring.supermarket.DTO.ProductDTO;
import org.example.spring.supermarket.entity.Cart;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.Product;

import java.util.List;
import java.util.Map;

public interface CartService {


    void addToCart(String sessionId, int productId, int quantity);

    void removeFromCart(String sessionId, int productId);

    void updateCartItemQuantity(String sessionId, int productId, int quantity);

    void clearCart(String sessionId);

    Map<ProductDTO, Integer> getCartItems(String sessionId);

    int getCartSize(String sessionId);

    double getCartTotal(String sessionId);

    Order checkout(String sessionId, Customer customer);
}
