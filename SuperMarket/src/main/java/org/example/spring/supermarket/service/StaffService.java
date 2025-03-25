package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<Staff> findAll();
    Optional<Staff> findById(int id);
    Staff save(Staff staff);
    void deleteById(int id);
    Optional<Staff> findByUserId(int userId);

    List<Staff> getAllStaff();
    Optional<Staff> getStaffById(int id);
    Optional<Staff> getStaffByUsername(String username);
    Optional<Staff> getStaffByEmail(String email);
    Staff saveStaff(Staff staff);
    void deleteStaff(int id);
    boolean authenticateStaff(String username, String password);
    boolean isAdmin(Staff staff);
}