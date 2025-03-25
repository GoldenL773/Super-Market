package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.repository.InventoryRepository;
import org.example.spring.supermarket.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        // Set last updated timestamp
        inventory.setLastUpdated(new Date());
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(int id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateInventoryQuantity(int inventoryId, int quantityChange) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findById(inventoryId);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            int newQuantity = inventory.getQuantity() + quantityChange;

            // Prevent negative inventory
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Cannot reduce inventory below zero");
            }

            inventory.setQuantity(newQuantity);
            inventory.setLastUpdated(new Date());
            inventoryRepository.save(inventory);
        } else {
            throw new IllegalArgumentException("Inventory not found with ID: " + inventoryId);
        }
    }

    @Override
    public Inventory getInventory(int productId) {
        return inventoryRepository.getInventoriesByProductId(productId);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public void update(int productId, int quantity) {
        inventoryRepository.updateInventory(productId, quantity);
    }
}
