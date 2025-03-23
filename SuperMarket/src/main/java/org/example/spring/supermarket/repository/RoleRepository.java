package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Role;
//import org.example.spring.supermarket.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Optional<Role> findByRoleName(String roleName);
}
