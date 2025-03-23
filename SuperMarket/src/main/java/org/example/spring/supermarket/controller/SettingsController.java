package org.example.spring.supermarket.controller;

import jakarta.validation.Valid;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Staff;

import org.example.spring.supermarket.DTO.UserSettingsDto;
import org.example.spring.supermarket.security.UserPrincipal;
import org.example.spring.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {

    private final UserService userService;

    @Autowired
    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String showSettingsPage(@AuthenticationPrincipal UserPrincipal currentUser, Model model) {
        UserSettingsDto userSettings = new UserSettingsDto();
        
        if (currentUser.isCustomer()) {
            Customer customer = userService.findCustomerById(currentUser.getId());
            
            userSettings.setFullName(customer.getFullName());
            userSettings.setEmail(customer.getEmail());
            userSettings.setPhoneNumber(customer.getPhoneNumber());
            userSettings.setAddress(customer.getAddress());
            userSettings.setGender(customer.getGender());
        } else {
            Staff staff = userService.findStaffById(currentUser.getId());
            
            userSettings.setFullName(staff.getFullName());
            userSettings.setEmail(staff.getEmail());
            userSettings.setPhoneNumber(staff.getPhoneNumber());
            userSettings.setAddress(staff.getAddress());
            userSettings.setGender(staff.getGender());
        }
        
        model.addAttribute("userSettings", userSettings);
        model.addAttribute("userType", currentUser.isCustomer() ? "customer" : "staff");
        
        return "setting";
    }

    @PostMapping("/settings")
    public String updateSettings(@AuthenticationPrincipal UserPrincipal currentUser,
                                @Valid @ModelAttribute("userSettings") UserSettingsDto userSettings,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "settings";
        }
        
        boolean updated = userService.updateUserProfile(currentUser.getId(), userSettings, currentUser.isCustomer());
        
        if (updated) {
            redirectAttributes.addFlashAttribute("success", "Your profile has been updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile. Please try again.");
        }
        
        return "redirect:/settings";
    }
}
