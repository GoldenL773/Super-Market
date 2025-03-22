package org.example.spring.supermarket.controller;


import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import org.example.spring.supermarket.dto.CustomerDto;
import org.example.spring.supermarket.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final CustomerService customerService;

    @Autowired
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customer", customerDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(
            @ModelAttribute("customer") @Valid CustomerDto customerDto,
            BindingResult result,
            Model model) {

        // Check for validation errors
        if (result.hasErrors()) {
            return "register";
        }

        // Check if passwords match
        if (!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "register";
        }

        // Check if username exists
        if (customerService.existsByUsername(customerDto.getUsername())) {
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }

        // Check if email exists
        if (customerService.existsByEmail(customerDto.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            return "register";
        }

        // Register the customer
        boolean isRegistered = customerService.registerCustomer(customerDto);
        if (!isRegistered) {
            model.addAttribute("registrationError", "An error occurred during registration");
            return "register";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
