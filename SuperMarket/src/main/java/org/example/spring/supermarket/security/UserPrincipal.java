package org.example.spring.supermarket.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private boolean isCustomer;

    public UserPrincipal(Integer id, String username, String password, String role, boolean isCustomer) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isCustomer = isCustomer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public boolean isCustomer() {
        return isCustomer;
    }
}
