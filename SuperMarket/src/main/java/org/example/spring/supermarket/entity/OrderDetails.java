package org.example.spring.supermarket.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detailsId", nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true, columnDefinition = "int")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true, columnDefinition = "int")
    private Product product;

    @Column(name = "quantity", nullable = false, columnDefinition = "int")
    private int quantity;

    @Column(name = "price", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal price;
}
