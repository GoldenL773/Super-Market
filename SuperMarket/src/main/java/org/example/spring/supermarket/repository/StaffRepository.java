package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Staff;
//import org.example.spring.supermarket.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    
    Optional<Staff> findByUsername(String username);
    
    Optional<Staff> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
