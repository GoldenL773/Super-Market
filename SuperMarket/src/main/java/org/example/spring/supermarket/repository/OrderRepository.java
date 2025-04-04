package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer(Customer customer);
    
    Page<Order> findByCustomer(Customer customer, Pageable pageable);
    @Query("SELECT od FROM OrderDetails od WHERE od.order.id = :orderId")
    List<OrderDetails> findOrderDetailsByOrderId(@Param("orderId") int orderId);

}