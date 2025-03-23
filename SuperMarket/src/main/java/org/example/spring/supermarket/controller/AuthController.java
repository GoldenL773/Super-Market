package org.example.spring.supermarket.controller;

import jakarta.validation.Valid;

import org.example.spring.supermarket.DTO.UserRegistrationDto;
import org.example.spring.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model, 
                        @RequestParam(value = "error", required = false) String error, 
                        @RequestParam(value = "logout", required = false) String logout) {
        
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        
        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.");
        }
        
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, 
                              BindingResult result, 
                              Model model) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        if (userService.isUsernameTaken(registrationDto.getUsername(), registrationDto.getUserType())) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        
        if (userService.isEmailTaken(registrationDto.getEmail(), registrationDto.getUserType())) {
            model.addAttribute("error", "Email already in use!");
            return "register";
        }
        
        userService.registerUser(registrationDto);
        
        return "redirect:/login?registered=true";
    }
    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
