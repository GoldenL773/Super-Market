package org.example.spring.supermarket.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.spring.supermarket.dto.CustomerDto;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Role;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.example.spring.supermarket.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class CustomerServiceiplm {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean registerCustomer(CustomerDto customerDto) {
        // Check if username or email already exists
        if (customerRepository.existsByUsername(customerDto.getUsername()) ||
                customerRepository.existsByEmail(customerDto.getEmail())) {
            return false;
        }

        // Create new customer
        Customer customer = new Customer();
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setFullName(customerDto.getFullName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setGender(customerDto.isGender());
        customer.setAddress(customerDto.getAddress());
        customer.setCreatedAt(new Date());

        // Assign default customer role if not specified
        Role role;
        if (customerDto.getRoleId() != 0) {
            role = roleRepository.findById(customerDto.getRoleId())
                    .orElse(roleRepository.findByRoleName("ROLE_CUSTOMER"));
        } else {
            role = roleRepository.findByRoleName("ROLE_CUSTOMER");
            if (role == null) {
                role = new Role("ROLE_CUSTOMER");
                roleRepository.save(role);
            }
        }

        customer.setRole(role);

        // Save customer
        customerRepository.save(customer);
        return true;
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
