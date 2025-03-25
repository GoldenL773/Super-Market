package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Supplier;
import org.example.spring.supermarket.repository.SupplierRepository;
import org.example.spring.supermarket.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> getSupplierById(int id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Optional<Supplier> getSupplierByName(String name) {
        return supplierRepository.findByName(name);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(int id) {
        supplierRepository.deleteById(id);
    }
}