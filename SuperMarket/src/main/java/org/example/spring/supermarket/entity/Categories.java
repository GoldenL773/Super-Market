package org.example.spring.supermarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false,columnDefinition = "nvarchar(100)")
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "description", nullable = false,  columnDefinition = "nvarchar(max)")
    private String description;
    @Column(name = "image", nullable = true, columnDefinition = "nvarchar(max)")
    private String image;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
