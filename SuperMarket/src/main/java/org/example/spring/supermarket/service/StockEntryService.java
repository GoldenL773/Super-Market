package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.entity.Product;
import org.example.spring.supermarket.entity.StockEntry;
import org.example.spring.supermarket.entity.Supplier;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StockEntryService {
    List<StockEntry> getAllStockEntries();
    Optional<StockEntry> getStockEntryById(int id);
    List<StockEntry> getStockEntriesByProduct(Product product);
    List<StockEntry> getStockEntriesByInventory(Inventory inventory);
    List<StockEntry> getStockEntriesBySupplier(Supplier supplier);
    List<StockEntry> getStockEntriesExpiringBefore(Date date);
    List<StockEntry> getStockEntriesReceivedBetween(Date startDate, Date endDate);
    StockEntry saveStockEntry(StockEntry stockEntry);
    void deleteStockEntry(int id);
}