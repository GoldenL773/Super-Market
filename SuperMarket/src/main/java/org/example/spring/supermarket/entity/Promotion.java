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
@Table(name = "Promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id", nullable = false, columnDefinition = "int")
    private int id;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(50)")
    private String code;

    @Column(name = "discount", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal discount;

    @Column(name = "valid_from", nullable = true, columnDefinition = "date")
    private Date validFrom;

    @Column(name = "valid_to", nullable = true, columnDefinition = "date")
    private Date validTo;

    @Column(name = "max_usage", nullable = true, columnDefinition = "int")
    private Integer maxUsage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = true, columnDefinition = "int")
    private Staff staff;

    @Column(name = "created_at", nullable = true, columnDefinition = "datetime")
    private Date createdAt;
}
