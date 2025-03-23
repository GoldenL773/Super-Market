package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Customer;
//import org.example.spring.supermarket.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Optional<Customer> findByUsername(String username);
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
