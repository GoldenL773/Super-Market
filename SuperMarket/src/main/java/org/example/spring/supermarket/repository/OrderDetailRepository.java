package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetails;
import org.example.spring.supermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrder_Id(int orderId);
    List<OrderDetails> findByOrder(Order order);
    @Query("SELECT COUNT(DISTINCT od.product) FROM OrderDetails od WHERE od.order.customer = :customer")
    int countDistinctProductByCustomer(@Param("customer") Customer customer);
}