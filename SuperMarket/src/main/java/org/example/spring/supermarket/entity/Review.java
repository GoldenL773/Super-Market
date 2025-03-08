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
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true, columnDefinition = "int")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true, columnDefinition = "int")
    private Product product;

    @Column(name = "rating", nullable = false, columnDefinition = "int")
    private int rating;

    @Column(name = "comment", nullable = true, columnDefinition = "text")
    private String comment;

    @Column(name = "response", nullable = true, columnDefinition = "text")
    private String response;

    @Column(name = "created_at", nullable = true, columnDefinition = "datetime")
    private Date createdAt;
}
