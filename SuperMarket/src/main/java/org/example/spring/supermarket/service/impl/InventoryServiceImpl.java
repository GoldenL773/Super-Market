package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.repository.InventoryRepository;
import org.example.spring.supermarket.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
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
