package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Categories;
import org.example.spring.supermarket.entity.Product;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Categories category);
    @Query("SELECT p FROM Product p WHERE p.category.id = " +
            "(SELECT p2.category.id FROM Product p2 WHERE p2.id = :productId) " +
            "AND p.id != :productId ORDER BY RAND()")
    List<Product> findRandomProductsByCategory(@Param("productId") int productId,Pageable pageable);
    Optional<Product> getProductById(int id);
    Page<Product> findProductByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findProductByCategory(Categories category, Pageable pageable);
    Page<Product> findProductByNameContainingIgnoreCaseAndCategory(String name, Categories category, Pageable pageable);
    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);
    Page<Product> findAllByOrderByCreatedAtAsc(Pageable pageable);
    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
