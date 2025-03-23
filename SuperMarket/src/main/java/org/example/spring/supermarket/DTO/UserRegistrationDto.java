package org.example.spring.supermarket.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 255, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Confirm password cannot be empty")
    private String confirmPassword;

    @NotEmpty(message = "Full name cannot be empty")
    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Size(max = 15, message = "Phone number must be less than 15 characters")
    private String phoneNumber;

    @NotNull(message = "Gender cannot be null")
    private Boolean gender;

    private String address;

    @NotEmpty(message = "User type cannot be empty")
    private String userType; // 'CUSTOMER' or 'STAFF'

    private Boolean agreeTerms;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getAgreeTerms() {
        return agreeTerms;
    }

    public void setAgreeTerms(Boolean agreeTerms) {
        this.agreeTerms = agreeTerms;
    }
}
