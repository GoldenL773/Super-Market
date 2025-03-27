package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.*;
import org.example.spring.supermarket.repository.OrderDetailRepository;
import org.example.spring.supermarket.repository.OrderRepository;
import org.example.spring.supermarket.repository.ProductRepository;
import org.example.spring.supermarket.service.OrderService;
import org.example.spring.supermarket.service.ProductService;
import org.example.spring.supermarket.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private InventoryService inventoryService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            ProductService productService,
                            ProductRepository productRepository, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    @Override
    @Transactional
    public Order createOrder(Order order, List<OrderDetails> orderDetails) {
        order.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        // Calculate total amount
        double totalAmount = 0.0;
        for (OrderDetails detail : orderDetails) {
            totalAmount += (Double.parseDouble(detail.getPrice().toString()) * detail.getQuantity());
        }
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));

        // Save the order first
        Order savedOrder = orderRepository.save(order);

        // Save order details and update product stock
        for (OrderDetails detail : orderDetails) {
            detail.setOrder(savedOrder);
            orderDetailRepository.save(detail);

            // Update product stock
            Inventory inventory = inventoryService.getInventory(detail.getProduct().getId());
            inventory.setQuantity(inventory.getQuantity() - detail.getQuantity());
            inventoryService.update(detail.getProduct().getId(), inventory.getQuantity());
        }

        return savedOrder;
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(int id) {
        // First delete all OrderDetails for this Order to avoid foreign key constraints
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            List<OrderDetails> details = orderDetailRepository.findByOrder(order);
            orderDetailRepository.deleteAll(details);

            // Then delete the Order
            orderRepository.deleteById(id);
        }
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderDetails> getOrderDetails(int orderId) {
        return orderRepository.findOrderDetailsByOrderId(orderId);
    }

    @Override
    public void updateStatus(int id, String status) {
        // Define the allowed status values
        List<String> allowedStatuses = Arrays.asList("Pending", "Shipped", "Delivered", "Cancelled");

        // Check if the provided status is valid
        if (!allowedStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        // Proceed with updating the status
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
    public Map<String, BigDecimal> getMonthlyIncome() {
        List<Order> validOrders = orderRepository.findByStatusNotIn(List.of("Pending", "Cancelled"));
        Map<String, BigDecimal> incomeByMonth = new TreeMap<>();

        for (Order order : validOrders) {
            String month = new SimpleDateFormat("yyyy-MM").format(order.getCreatedAt());
            incomeByMonth.put(month, incomeByMonth.getOrDefault(month, BigDecimal.ZERO).add(order.getTotalAmount()));
        }

        return incomeByMonth;
    }

    // Trong OrderService.java
    public BigDecimal calculateTotalSpentByCustomer(Customer customer) {
        return orderRepository.calculateTotalSpentByCustomer(customer);
    }

    @Override
    public int countByCustomer(Optional<Customer> customer) {
        return customer.map(orderRepository::countByCustomer).orElse(0);
    }


}