package org.example.spring.supermarket.service;


import org.example.spring.supermarket.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getProductRandomByCategory(int productId);
    Optional<ProductDTO> getProductById(int id);
    List<ProductDTO> getProductByCategoryId(int id);
    List<ProductDTO> getAll();
    ProductDTO getById(int id);
    Page<ProductDTO> searchProducts(String query, Pageable pageable);
    Page<ProductDTO> searchProductsByCategoryAndQuery(String query, int categoryId, Pageable pageable);
    Page<ProductDTO> searchProductsByCategory(int categoryId, Pageable pageable);
    Page<ProductDTO> sortProducts(Pageable pageable);
}
