package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    @Query("SELECT i.product.id, COALESCE(SUM(i.quantity), 0) FROM Inventory i GROUP BY i.product.id")
    List<Object[]> findAllProductQuantities();

    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId")
    Optional<Inventory> findByProductId(@Param("productId") int productId);



}
