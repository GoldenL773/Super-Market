package org.example.spring.supermarket.controller;

import org.example.spring.supermarket.dto.CustomerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public  String login(){
        return "login.html";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        CustomerDto customerDto = CustomerService.findByUsername(username);
        if (customerDto != null && customerDto.getPassword().equals(password)) {
            return "redirect:/"; // Redirect to the index page on successful login
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login"; // Stay on the login page and show an error
        }
}
