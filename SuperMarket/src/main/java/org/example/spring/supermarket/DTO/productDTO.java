package org.example.spring.supermarket.DTO;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class productDTO {
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.1", message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Ảnh không được để trống")
    private MultipartFile image; // Sử dụng MultipartFile để upload ảnh
    @Size(min = 10, message = "Mô tả phải có ít nhất 10 ký tự")
    private String description;
    @NotNull(message = "Danh mục không được để trống")
    private Integer categoryId; // Sử dụng Integer thay vì Long để phù hợp với int id
    private Integer quantity; // Thêm trường số lượng sản phẩm
    private BigDecimal PurchasePrice;

    public BigDecimal getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
