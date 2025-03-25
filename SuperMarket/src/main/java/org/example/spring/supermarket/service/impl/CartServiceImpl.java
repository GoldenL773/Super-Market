package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.dto.ProductDTO;
import org.example.spring.supermarket.entity.*;
import org.example.spring.supermarket.repository.CartRepository;
import org.example.spring.supermarket.service.CartService;
import org.example.spring.supermarket.service.CategoryService;
import org.example.spring.supermarket.service.OrderService;
import org.example.spring.supermarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final ProductService productService;
    private final OrderService orderService;
    private CategoryService categoryService;
    private CartRepository cartRepository;
    // In-memory storage for shopping carts (session ID -> cart items)
    private final Map<String, Map<Integer, Integer>> carts = new ConcurrentHashMap<>();

    @Autowired
    public CartServiceImpl(ProductService productService, OrderService orderService,CategoryService categoryService, CartRepository cartRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.cartRepository = cartRepository;
    }

    @Override
    public void addToCart(Customer customer, Product product, int quantity) {
        Optional<Cart> existingCartItem = cartRepository.findByCustomerAndProduct(customer, product);
        if (existingCartItem.isPresent()) {
            Cart cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);
        } else {
            Cart cartItem = new Cart();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
        }
    }

    @Override
    public void updateCartItemQuantity(Customer customer, Product product, int quantity) {
        Optional<Cart> existingCartItem = cartRepository.findByCustomerAndProduct(customer, product);
        if (existingCartItem.isPresent()) {
            Cart cartItem = existingCartItem.get();
            if (quantity > 0) {
                cartItem.setQuantity(quantity);
                cartRepository.save(cartItem);
            } else {
                cartRepository.deleteByCustomerAndProduct(customer, product);
            }
        }
    }

    @Override
    public void removeFromCart(Customer customer, Product product) {
        cartRepository.deleteByCustomerAndProduct(customer, product);
    }

    @Override
    public void clearCart(Customer customer) {
        cartRepository.deleteByCustomer(customer);
    }

    @Override
    public Map<ProductDTO, Integer> getCartItems(Customer customer) {
        List<Cart> cartList = cartRepository.findByCustomer(customer);
        return cartList.stream().collect(Collectors.toMap(
                cart -> productService.getProductById(cart.getProduct().getId()).orElse(null),
                Cart::getQuantity,
                (oldValue, newValue) -> newValue,
                HashMap::new
        ));
    }

    @Override
    public double getCartTotal(Customer customer) {
        Double total = cartRepository.calculateCartTotalByCustomer(customer);
        return total != null ? total : 0.0;
    }

    @Override
    public Order checkout(Customer customer) {
        Map<ProductDTO, Integer> cartItems = getCartItems(customer);

        if (cartItems.isEmpty()) {
            return null;
        }

        // Create a new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("Pending");

        // Create order details
        List<OrderDetails> orderDetails = new ArrayList<>();
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
            OrderDetails detail = new OrderDetails();
            detail.setProduct(p);
            detail.setQuantity(quantity);
            detail.setPrice(product.getPrice());

            orderDetails.add(detail);
        }
        // Create the order with order details
        Order savedOrder = orderService.createOrder(order, orderDetails);
        // Clear the cart after successful checkout
        clearCart(customer);
        return savedOrder;
    }

}
