package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.entity.Product;
import org.example.spring.supermarket.entity.StockEntry;
import org.example.spring.supermarket.entity.Supplier;
import org.example.spring.supermarket.repository.StockEntryRepository;
import org.example.spring.supermarket.service.InventoryService;
import org.example.spring.supermarket.service.StockEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockEntryServiceImpl implements StockEntryService {

    private final StockEntryRepository stockEntryRepository;
    private final InventoryService inventoryService;

    @Autowired
    public StockEntryServiceImpl(StockEntryRepository stockEntryRepository,
                                 InventoryService inventoryService) {
        this.stockEntryRepository = stockEntryRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public List<StockEntry> getAllStockEntries() {
        return stockEntryRepository.findAll();
    }

    @Override
    public Optional<StockEntry> getStockEntryById(int id) {
        return stockEntryRepository.findById(id);
    }

    @Override
    public List<StockEntry> getStockEntriesByProduct(Product product) {
        return stockEntryRepository.findByProduct(product);
    }

    @Override
    public List<StockEntry> getStockEntriesByInventory(Inventory inventory) {
        return stockEntryRepository.findByInventory(inventory);
    }

    @Override
    public List<StockEntry> getStockEntriesBySupplier(Supplier supplier) {
        return stockEntryRepository.findBySupplier(supplier);
    }

    @Override
    public List<StockEntry> getStockEntriesExpiringBefore(Date date) {
        return stockEntryRepository.findByExpiryDateBefore(date);
    }

    @Override
    public List<StockEntry> getStockEntriesReceivedBetween(Date startDate, Date endDate) {
        return stockEntryRepository.findByReceivedAtBetween(startDate, endDate);
    }

    @Override
    @Transactional
    public StockEntry saveStockEntry(StockEntry stockEntry) {
        // Set received time if new entry
        if (stockEntry.getId() == 0) {
            stockEntry.setReceivedAt(new Date());
        }

        // Update inventory quantity
        if (stockEntry.getInventory() != null) {
            inventoryService.updateInventoryQuantity(
                    stockEntry.getInventory().getId(), stockEntry.getQuantity()
            );
        }

        return stockEntryRepository.save(stockEntry);
    }

    @Override
    @Transactional
    public void deleteStockEntry(int id) {
        Optional<StockEntry> stockEntryOpt = stockEntryRepository.findById(id);

        if (stockEntryOpt.isPresent()) {
            StockEntry stockEntry = stockEntryOpt.get();

            // Update inventory (reduce by the stock entry quantity)
            if (stockEntry.getInventory() != null) {
                inventoryService.updateInventoryQuantity(
                        stockEntry.getInventory().getId(), -stockEntry.getQuantity()
                );
            }

            stockEntryRepository.deleteById(id);
        }
    }
}