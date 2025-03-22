package org.example.spring.supermarket.controller;

import ch.qos.logback.core.model.Model;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.security.CustomerUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    @GetMapping("/shop")
    public String showShopPage(@AuthenticationPrincipal CustomerUserDetails customerUserDetails, Model model) {
        // Get the authenticated customer
        Customer customer = customerUserDetails.getCustomer();
        model.addAttribute("customer", customer);
        return "shop";
    }
}
