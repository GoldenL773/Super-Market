package org.example.spring.supermarket.service;


import org.example.spring.supermarket.dto.ProductDTO;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.Product;

import java.util.Map;

public interface CartService {

    void addToCart(Customer customer, Product product, int quantity);

    void updateCartItemQuantity(Customer customer, Product product, int quantity);

    void removeFromCart(Customer customer, Product product);

    void clearCart(Customer customer);

    Map<ProductDTO, Integer> getCartItems(Customer customer);

    double getCartTotal(Customer customer);

    Order checkout(Customer customer);
}
