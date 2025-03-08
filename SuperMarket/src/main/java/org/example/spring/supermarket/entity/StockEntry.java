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
@Table(name = "StockEntry")
public class StockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_entry_id", nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "int")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false, columnDefinition = "int")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = true, columnDefinition = "int")
    private Supplier supplier;

    @Column(name = "batch_number", nullable = true, length = 50, columnDefinition = "nvarchar(50)")
    private String batchNumber;

    @Column(name = "quantity", nullable = false, columnDefinition = "int")
    private int quantity;

    @Column(name = "purchase_price", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal purchasePrice;

    @Column(name = "expiry_date", nullable = true, columnDefinition = "date")
    private Date expiryDate;

    @Column(name = "received_at", nullable = true, columnDefinition = "datetime")
    private Date receivedAt;
}
