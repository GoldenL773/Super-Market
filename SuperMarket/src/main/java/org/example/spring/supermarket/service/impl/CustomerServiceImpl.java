package org.example.spring.supermarket.service.impl;


import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.example.spring.supermarket.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository
                .findFirstByUsername(username)
                .orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        return List.of();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        // Encode password before saving
        customer.setPassword(customer.getPassword());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        // Check if we need to encode the password (if it was changed)
        if (customer.getCustomerId() != 0) {
            Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerId());
            if (existingCustomer.isPresent() && !existingCustomer.get().getPassword().equals(customer.getPassword())) {
                customer.setPassword(customer.getPassword());
            }
        }
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean authenticateCustomer(String email, String password) {
        return false;
    }

}