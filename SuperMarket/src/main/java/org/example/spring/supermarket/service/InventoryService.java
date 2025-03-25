package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Inventory;

public interface InventoryService {
    Inventory getInventory(int productId);
    Inventory save(Inventory inventory);
    void update(int productId, int quantity);

    Inventory saveInventory(Inventory inventory);
    void deleteInventory(int id);
    void updateInventoryQuantity(int inventoryId, int quantityChange);
}
