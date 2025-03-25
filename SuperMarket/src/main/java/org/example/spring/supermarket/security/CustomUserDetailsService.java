package org.example.spring.supermarket.security;

import org.example.spring.supermarket.entity.Customer;
//import org.example.spring.supermarket.model.Customer;
//import org.example.spring.supermarket.model.Staff;
import org.example.spring.supermarket.entity.Staff;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.example.spring.supermarket.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;

    @Autowired
    public CustomUserDetailsService(CustomerRepository customerRepository, StaffRepository staffRepository) {
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith("customer:")) {
            username = username.substring("customer:".length());
        }

        if (username.startsWith("staff:")) {
            username = username.substring("staff:".length());
        }
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    customer.get().getUsername(),
                    customer.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(customer.get().getRole().getRoleName()))
            );
        }
        Optional<Staff> staff = staffRepository.findByUsername(username);
        if (staff.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    staff.get().getUsername(),
                    staff.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(staff.get().getRole().getRoleName()))
            );
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }


}
