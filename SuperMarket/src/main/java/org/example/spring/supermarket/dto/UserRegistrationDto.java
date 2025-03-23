package org.example.spring.supermarket.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "User type is required")
    private String userType;
    
    @NotEmpty(message = "Confirm password cannot be empty")
    private String confirmPassword;
    
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotEmpty(message = "Full name cannot be empty")
    private String fullName;
    
    private String phoneNumber;
    
    private Boolean gender = true;
    
    private String address;


}
