package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Role;
import org.example.spring.supermarket.entity.Staff;
import org.example.spring.supermarket.DTO.UserRegistrationDto;
import org.example.spring.supermarket.DTO.UserSettingsDto;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.example.spring.supermarket.repository.RoleRepository;
import org.example.spring.supermarket.repository.StaffRepository;
import org.example.spring.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(CustomerRepository customerRepository,
                          StaffRepository staffRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public Customer findCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public Staff findStaffById(Integer id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
    }

    @Override
    public Customer findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found with username: " + username));
    }

    @Override
    public Staff findStaffByUsername(String username) {
        return staffRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Staff not found with username: " + username));
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationDto registrationDto) {
        String userType = registrationDto.getUserType();

        if ("CUSTOMER".equalsIgnoreCase(userType)) {
            registerCustomer(registrationDto);
        } else if ("STAFF".equalsIgnoreCase(userType)) {
            registerStaff(registrationDto);
        } else {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    private void registerCustomer(UserRegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setUsername(registrationDto.getUsername());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customer.setFullName(registrationDto.getFullName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setGender(registrationDto.getGender());
        customer.setAddress(registrationDto.getAddress());
        customer.setCreatedAt(LocalDateTime.now());

        // Assign customer role
        Optional<Role> customerRole = roleRepository.findByRoleName("ROLE_CUSTOMER");
        if (customerRole.isPresent()) {
            customer.setRole(customerRole.get());
        } else {
            Role newRole = new Role("ROLE_CUSTOMER");
            roleRepository.save(newRole);
            customer.setRole(newRole);
        }

        customerRepository.save(customer);
    }

    private void registerStaff(UserRegistrationDto registrationDto) {
        Staff staff = new Staff();
        staff.setUsername(registrationDto.getUsername());
        staff.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        staff.setFullName(registrationDto.getFullName());
        staff.setEmail(registrationDto.getEmail());
        staff.setPhoneNumber(registrationDto.getPhoneNumber());
        staff.setGender(registrationDto.getGender());
        staff.setAddress(registrationDto.getAddress());
        staff.setCreatedAt(LocalDateTime.now());

        // Assign staff role
        Optional<Role> staffRole = roleRepository.findByRoleName("ROLE_STAFF");
        if (staffRole.isPresent()) {
            staff.setRole(staffRole.get());
        } else {
            Role newRole = new Role("ROLE_STAFF");
            roleRepository.save(newRole);
            staff.setRole(newRole);
        }

        staffRepository.save(staff);
    }

    @Override
    @Transactional
    public boolean updateUserProfile(Integer userId, UserSettingsDto settingsDto, boolean isCustomer) {
        try {
            if (isCustomer) {
                Customer customer = findCustomerById(userId);

                customer.setFullName(settingsDto.getFullName());
                customer.setEmail(settingsDto.getEmail());
                customer.setPhoneNumber(settingsDto.getPhoneNumber());
                customer.setGender(settingsDto.getGender());
                customer.setAddress(settingsDto.getAddress());

                // Update password if provided
                if (settingsDto.getNewPassword() != null && !settingsDto.getNewPassword().isEmpty()) {
                    customer.setPassword(passwordEncoder.encode(settingsDto.getNewPassword()));
                }

                customerRepository.save(customer);
            } else {
                Staff staff = findStaffById(userId);

                staff.setFullName(settingsDto.getFullName());
                staff.setEmail(settingsDto.getEmail());
                staff.setPhoneNumber(settingsDto.getPhoneNumber());
                staff.setGender(settingsDto.getGender());
                staff.setAddress(settingsDto.getAddress());

                // Update password if provided
                if (settingsDto.getNewPassword() != null && !settingsDto.getNewPassword().isEmpty()) {
                    staff.setPassword(passwordEncoder.encode(settingsDto.getNewPassword()));
                }

                staffRepository.save(staff);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isUsernameTaken(String username, String userType) {
        if ("CUSTOMER".equalsIgnoreCase(userType)) {
            return customerRepository.existsByUsername(username);
        } else if ("STAFF".equalsIgnoreCase(userType)) {
            return staffRepository.existsByUsername(username);
        }

        return false;
    }

    @Override
    public boolean isEmailTaken(String email, String userType) {
        if ("CUSTOMER".equalsIgnoreCase(userType)) {
            return customerRepository.existsByEmail(email);
        } else if ("STAFF".equalsIgnoreCase(userType)) {
            return staffRepository.existsByEmail(email);
        }

        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    customer.get().getUsername(),
                    customer.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + customer.get().getRole().getRoleName()))
            );
        }
        Optional<Staff> staff = staffRepository.findByUsername(username);
        if (staff.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    staff.get().getUsername(),
                    staff.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + staff.get().getRole().getRoleName()))
            );
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
