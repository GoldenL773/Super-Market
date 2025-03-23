package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Staff;
import org.example.spring.supermarket.DTO.UserRegistrationDto;
import org.example.spring.supermarket.DTO.UserSettingsDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Customer findCustomerById(Integer id);

    Staff findStaffById(Integer id);

    Customer findCustomerByUsername(String username);

    Staff findStaffByUsername(String username);

    void registerUser(UserRegistrationDto registrationDto);

    boolean updateUserProfile(Integer userId, UserSettingsDto settingsDto, boolean isCustomer);

    boolean isUsernameTaken(String username, String userType);

    boolean isEmailTaken(String email, String userType);
     UserDetails loadUserByUsername(String username);
}
