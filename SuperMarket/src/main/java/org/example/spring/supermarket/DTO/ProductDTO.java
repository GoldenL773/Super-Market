// Java
package org.example.spring.supermarket.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, columnDefinition = "int")
    private int id;
    @NotEmpty(message = "Product name must not be empty")
    private String name;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Image must not be empty")
    private MultipartFile image; // using MultipartFile for upload

    @Size(min = 10, message = "Description must have at least 10 characters")
    private String description;

    @NotNull(message = "Category must not be null")
    private Integer categoryId;

    private Integer quantity;
    private BigDecimal purchasePrice;
    private Date createdAt;
}