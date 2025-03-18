package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
        Inventory getInventoriesByProductId(int productId);
        @Modifying
        @Transactional
        @Query("UPDATE Inventory s SET s.quantity = :quantity WHERE s.product.id = :productId")
        void updateInventory(@Param("productId") int productId, @Param("quantity") int quantity);
}
