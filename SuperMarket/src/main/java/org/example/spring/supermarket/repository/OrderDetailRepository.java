package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetail;
import org.example.spring.supermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    
    List<OrderDetail> findByOrder(Order order);
    
    List<OrderDetail> findByProduct(Product product);
    
    @Query("SELECT od.product.id, SUM(od.quantity) FROM OrderDetail od GROUP BY od.product.id ORDER BY SUM(od.quantity) DESC")
    List<Object[]> findTopSellingProducts();
}