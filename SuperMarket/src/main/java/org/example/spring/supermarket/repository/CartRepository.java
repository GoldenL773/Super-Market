package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT SUM(c.quantity * p.price) FROM Cart c JOIN c.product p WHERE c.customer.id = ?1")

    Double calculateCartTotal(Integer customerId);

    void deleteByCustomerId(Integer customerId);
    List<Cart> findByCustomerId(Integer customerId);

    Optional<Cart> findByCustomerIdAndProductId(Integer customerId, Integer productId);
}
