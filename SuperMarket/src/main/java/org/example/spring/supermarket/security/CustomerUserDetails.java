package org.example.spring.supermarket.security;

import org.example.spring.supermarket.entity.Customer;

import java.util.ArrayList;

public class CustomerUserDetails implements UserDetails{
    private final Customer customer;

    public CustomerUserDetails(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (customer.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(customer.getRole().getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Get the logged-in customer
    public Customer getCustomer() {
        return customer;
    }
}
