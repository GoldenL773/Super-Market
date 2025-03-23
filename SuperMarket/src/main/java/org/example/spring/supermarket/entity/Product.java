package org.example.spring.supermarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, columnDefinition = "int")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @Column(name = "image", nullable = true, columnDefinition = "nvarchar(max)")
    private String image;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(100)")
    private String name;

    @Column(name = "description", nullable = true, columnDefinition = "nvarchar(max)")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal price;

    @Column(name = "created_at", nullable = true, columnDefinition = "datetime")
    private Date createdAt;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<Cart> cartItems;
}
