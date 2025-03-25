package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.entity.Product;
import org.example.spring.supermarket.entity.StockEntry;
import org.example.spring.supermarket.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockEntryRepository extends JpaRepository<StockEntry, Integer> {
    List<StockEntry> findByProduct(Product product);
    List<StockEntry> findByInventory(Inventory inventory);
    List<StockEntry> findBySupplier(Supplier supplier);
    List<StockEntry> findByExpiryDateBefore(Date date);
    List<StockEntry> findByReceivedAtBetween(Date startDate, Date endDate);
}