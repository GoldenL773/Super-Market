package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.dto.ProductDTO;
import org.example.spring.supermarket.entity.Cart;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByCustomer(Customer customer);

    Optional<Cart> findByCustomerAndProduct(Customer customer, Product product);

    @Modifying
    @Transactional
    void deleteByCustomer(Customer customer);

    @Modifying
    @Transactional
    void deleteByCustomerAndProduct(Customer customer, Product product);

    @Query("SELECT SUM(c.quantity * c.product.price) FROM Cart c WHERE c.customer = :customer")
    Double calculateCartTotalByCustomer(@Param("customer") Customer customer);
}
