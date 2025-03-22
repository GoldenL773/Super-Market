package org.example.spring.supermarket.service;

import org.example.spring.supermarket.dto.CustomerDto;
import org.example.spring.supermarket.entity.Customer;

public interface CustomerService {
    boolean registerCustomer(CustomerDto customerDto);

    /**
     * Find a customer by username
     * @param username customer's username
     * @return customer entity if found, null otherwise
     */
    Customer findByUsername(String username);

    /**
     * Check if a username is already taken
     * @param username username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if an email is already taken
     * @param email email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
