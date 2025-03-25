package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Role;
import org.example.spring.supermarket.entity.Staff;
import org.example.spring.supermarket.repository.StaffRepository;
import org.example.spring.supermarket.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<Staff> findAll() {
        return List.of();
    }

    @Override
    public Optional<Staff> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Staff save(Staff staff) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Optional<Staff> findByUserId(int userId) {
        return Optional.empty();
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> getStaffById(int id) {
        return staffRepository.findById(id);
    }

    @Override
    public Optional<Staff> getStaffByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    @Override
    public Optional<Staff> getStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public Staff saveStaff(Staff staff) {
        // Encrypt password if it's a new staff member or password has been changed
        if (staff.getStaffId() == 0 || (staff.getPassword() != null && !staff.getPassword().startsWith("$2a$"))) {
            staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        }
        return staffRepository.save(staff);
    }

    @Override
    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
    }

    @Override
    public boolean authenticateStaff(String username, String password) {
        Optional<Staff> staffOpt = staffRepository.findByUsername(username);

        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            return passwordEncoder.matches(password, staff.getPassword());
        }

        return false;
    }

    @Override
    public boolean isAdmin(Staff staff) {
        if (staff != null && staff.getRole() != null) {
            return "admin".equalsIgnoreCase(staff.getRole().getRoleName());
        }
        return false;
    }
}