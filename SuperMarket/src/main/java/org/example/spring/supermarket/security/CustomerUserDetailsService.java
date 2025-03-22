package org.example.spring.supermarket.security;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerUserDetailsService implements UserDetailsService{
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);

        if (customer == null) {
            throw new UsernameNotFoundException("No customer found with username: " + username);
        }

        return new CustomerUserDetails(customer);
    }
}
}
