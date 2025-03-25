package org.example.spring.supermarket.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock_entries")
public class StockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_entry_id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = true)
    private Supplier supplier;

    @Column(name = "batch_number", nullable = true, length = 50)
    private String batchNumber;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "expiry_date", nullable = true)
    private Date expiryDate;

    @Column(name = "received_at", nullable = true)
    private Date receivedAt;
}