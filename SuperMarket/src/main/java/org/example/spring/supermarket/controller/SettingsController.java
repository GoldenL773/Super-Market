package org.example.spring.supermarket.controller;

import jakarta.validation.Valid;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Staff;
import org.example.spring.supermarket.DTO.UserSettingsDto;
import org.example.spring.supermarket.security.UserPrincipal;
import org.example.spring.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String showSettingsPage(Model model) {
        // Get the Authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSettingsDto userSettings = new UserSettingsDto();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            // Cast the principal to UserPrincipal
            UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();

            if (currentUser.isCustomer()) {
                Customer customer = userService.findCustomerById(currentUser.getId());
                userSettings.setFullName(customer.getFullName());
                userSettings.setEmail(customer.getEmail());
                userSettings.setPhoneNumber(customer.getPhoneNumber());
                userSettings.setAddress(customer.getAddress());
                userSettings.setGender(customer.getGender());
                model.addAttribute("userType", "customer");
            } else {
                Staff staff = userService.findStaffById(currentUser.getId());
                userSettings.setFullName(staff.getFullName());
                userSettings.setEmail(staff.getEmail());
                userSettings.setPhoneNumber(staff.getPhoneNumber());
                userSettings.setAddress(staff.getAddress());
                userSettings.setGender(staff.getGender());
                model.addAttribute("userType", "staff");
            }
        } else {
            // Handle unauthenticated user (should redirect to login due to SecurityConfig)
            model.addAttribute("error", "You must be logged in to access this page.");
            model.addAttribute("userType", "anonymous");
        }

        model.addAttribute("userSettings", userSettings);
        return "setting"; // Note: Should be "settings" to match your template name
    }

    @PostMapping("/settings")
    public String updateSettings(@Valid @ModelAttribute("userSettings") UserSettingsDto userSettings,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        // Get the Authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (result.hasErrors()) {
            return "settings";
        }

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
            boolean updated = userService.updateUserProfile(currentUser.getId(), userSettings, currentUser.isCustomer());

            if (updated) {
                redirectAttributes.addFlashAttribute("success", "Your profile has been updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to update profile. Please try again.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to update your profile.");
        }

        return "redirect:/settings";
    }
}