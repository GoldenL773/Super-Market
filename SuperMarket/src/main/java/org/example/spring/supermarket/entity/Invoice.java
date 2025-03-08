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
@Table(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true, columnDefinition = "int")
    private Order order;

    @Column(name = "payment_method", nullable = false, length = 50, columnDefinition = "varchar(50)")
    private String paymentMethod;

    @Column(name = "amount", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal amount;

    @Column(name = "status", nullable = false, length = 50, columnDefinition = "varchar(50)")
    private String status;

    @Column(name = "created_at", nullable = true, columnDefinition = "datetime")
    private Date createdAt;
}
