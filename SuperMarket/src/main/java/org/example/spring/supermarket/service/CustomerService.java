package org.example.spring.supermarket.service;


import org.example.spring.supermarket.DTO.UserRegistrationDto;
import org.example.spring.supermarket.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer findByUsername(String username);

    List<Customer> getAllCustomers();

    Page<Customer> getAllCustomers(Pageable pageable);

    Optional<Customer> getCustomerById(int id);

    Optional<Customer> getCustomerByEmail(String email);

    List<Customer> searchCustomers(String keyword);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(int id);

    boolean authenticateCustomer(String email, String password);
}
