package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
        Inventory getInventoriesByProductId(int productId);
        @Modifying
        @Transactional
        @Query("UPDATE Inventory s SET s.quantity = :quantity WHERE s.product.id = :productId")
        void updateInventory(@Param("productId") int productId, @Param("quantity") int quantity);

        @Query("SELECT i.product.id, COALESCE(SUM(i.quantity), 0) FROM Inventory i GROUP BY i.product.id")
        List<Object[]> findAllProductQuantities();
    
        @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId")
        Optional<Inventory> findByProductId(@Param("productId") int productId);

        List<Inventory> findByProduct(Product product);
//        Optional<Inventory> findByProductId(int productId);
@Modifying
@Transactional
@Query("DELETE FROM Inventory i WHERE i.product.id = :productId")
void deleteByProductId(@Param("productId") int productId);
}
