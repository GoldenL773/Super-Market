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
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "int")
    private Product product;

    @Column(name = "quantity", nullable = false, columnDefinition = "int")
    private int quantity;

    @Column(name = "purchase_date", nullable = true, columnDefinition = "date")
    private Date purchaseDate;

    @Column(name = "purchase_price", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal purchasePrice;

    @Column(name = "last_updated", nullable = true, columnDefinition = "datetime")
    private Date lastUpdated;
}
