package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    Optional<Order> getOrderById(int id);
    List<Order> getOrdersByCustomer(Customer customer);
    Order createOrder(Order order, List<OrderDetails> orderDetails);
    Order updateOrder(Order order);
    void deleteOrder(int id);

    void updateStatus(int id, String status);
    List<Order> findAll();
}
