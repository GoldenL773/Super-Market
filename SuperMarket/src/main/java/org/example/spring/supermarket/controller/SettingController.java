package org.example.spring.supermarket.controller;

import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.entity.User;
import org.example.spring.supermarket.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // Hiển thị trang settings
    @GetMapping
    public String showSettingsPage( Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null ) {
            return "redirect:/login";
        }

        model.addAttribute("customer", customer);
        return "setting";
    }

    // Xử lý cập nhật thông tin
    @PostMapping
    public String updateCustomer(@ModelAttribute("customer") Customer customer, HttpSession session) {
        Customer current = (Customer) session.getAttribute("customer");
        if (current == null) {
            return "redirect:/login";
        }

        current.setFullName(customer.getFullName());
        current.setEmail(customer.getEmail());
        current.setPhoneNumber(customer.getPhoneNumber());
        current.setAddress(customer.getAddress());
        current.setUsername(customer.getUsername());

        customerRepository.save(current);
        session.setAttribute("customer", customer);

        return "redirect:/setting?success";
    }
}
