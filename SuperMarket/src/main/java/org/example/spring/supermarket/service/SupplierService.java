package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(int id);
    Optional<Supplier> getSupplierByName(String name);
    Supplier saveSupplier(Supplier supplier);
    void deleteSupplier(int id);
}