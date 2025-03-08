package org.example.spring.supermarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "description", nullable = false,  columnDefinition = "nvarchar(max)")
    private String description;

}
