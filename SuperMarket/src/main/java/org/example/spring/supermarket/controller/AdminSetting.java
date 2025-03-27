package org.example.spring.supermarket.controller;

import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.entity.Staff;
import org.example.spring.supermarket.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/setting")
public class AdminSetting {

    @Autowired
    private StaffRepository staffRepository;

    // Show staff settings page
    @GetMapping
    public String showStaffSettingsPage(Model model, HttpSession session) {
        Staff staff = (Staff) session.getAttribute("staff");

        // Ensure the staff is present in the session, else redirect to login
        if (staff == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("staff", staff);
        return "admin/set"; // Assuming the template is /admin/set.html
    }

    // Update staff information
    @PostMapping
    public String updateStaffInfo(@ModelAttribute("staff") Staff updatedStaff, HttpSession session) {
        Staff current = (Staff) session.getAttribute("staff");

        // Check if the staff object is not found in the session
        if (current == null) {
            return "redirect:/admin/login"; // If no staff session, redirect to login
        }

        // Update the staff's details
        current.setFullName(updatedStaff.getFullName());
        current.setEmail(updatedStaff.getEmail());
        current.setPhoneNumber(updatedStaff.getPhoneNumber());
        current.setAddress(updatedStaff.getAddress());
        current.setUsername(updatedStaff.getUsername());

        // Save the updated staff back to the repository
        staffRepository.save(current);

        // Update the session with the new staff object
        session.setAttribute("staff", current);

        return "redirect:/admin/setting?success"; // Redirect with a success flag
    }
}
