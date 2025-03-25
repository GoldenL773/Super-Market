package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findCustomerByUsernameContainingIgnoreCase(String username);


    
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
