package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface OrderService {
    Optional<Order> getOrderById(int id);
    List<Order> getOrdersByCustomer(Customer customer);
    Order createOrder(Order order, List<OrderDetail> orderDetails);
    Order updateOrder(Order order);
    void deleteOrder(int id);


}
