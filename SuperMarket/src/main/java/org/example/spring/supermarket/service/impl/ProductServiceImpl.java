package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.DTO.ProductDTO;
import org.example.spring.supermarket.entity.Categories;
import org.example.spring.supermarket.entity.Product;
import org.example.spring.supermarket.repository.CategoryRepository;
import org.example.spring.supermarket.repository.ProductRepository;
import org.example.spring.supermarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<ProductDTO> getProductRandomByCategory(int productId) {
        Pageable pageable = PageRequest.of(0, 5); // Lấy 5 sản phẩm
        List<Product> products = productRepository.findRandomProductsByCategory(productId, pageable);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(this::convertToDTO);
    }

    public Map<Integer, List<ProductDTO>> getRandomProductsByCategory(int count) {
        // 1. Get all categories.
        List<Categories> allCategories = categoryRepository.findAll();

        // 2. Create a map to store random products for each category.
        Map<Integer, List<ProductDTO>> productsByCategory = new HashMap<>();

        // 3. Iterate through each category.
        for (Categories category : allCategories) {
            // 4. Get products for the current category.
            List<Product> productsInCategory = productRepository.findByCategory(category);

            // Handle not enough product
            if (productsInCategory.size() <= count) {
                System.out.println("Not enough products in category " + category.getName() + ". Returning all available.");
                productsByCategory.put(category.getId(), productsInCategory.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()));
                continue;
            }
            // 5. Shuffle the products in the category.
            Collections.shuffle(productsInCategory);

            // 6. Select the first 'count' products.
            List<Product> randomProducts = productsInCategory.subList(0, count);

            // 7. Convert to DTOs and store in the map.
            List<ProductDTO> randomProductDTOs = randomProducts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            productsByCategory.put(category.getId(), randomProductDTOs);
        }

        // 8. Return the map.
        return productsByCategory;
    }

    @Override
    public List<ProductDTO> getProductByCategoryId(int id) {
        List<Product> productsInCategory = productRepository.findByCategory(categoryRepository.getReferenceById(id));
        return productsInCategory.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> list = productRepository.findAll();
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getById(int id) {
        return productRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> searchProducts(String query, Pageable pageable) {
        return productRepository.findProductByNameContainingIgnoreCase(query, pageable).map(this::convertToDTO);
    }

    @Override
    public Page<ProductDTO> searchProductsByCategoryAndQuery(String query, int categoryId, Pageable pageable) {
        return productRepository.findProductByNameContainingIgnoreCaseAndCategory(query, categoryRepository.getReferenceById(categoryId), pageable).map(this::convertToDTO);
    }

    @Override
    public Page<ProductDTO> searchProductsByCategory(  int categoryId, Pageable pageable) {
        return productRepository.findProductByCategory(categoryRepository.getReferenceById(categoryId), pageable).map(this::convertToDTO);
    }

    @Override
    public Page<ProductDTO> sortProducts(Pageable pageable) {
        List<Sort.Order> orders = new ArrayList<>();
        pageable.getSort().forEach(orders::add);
        if (orders.isEmpty()) {
            // No sorting specified, return all products sorted by price ascending
            return productRepository.findAll(Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber())).map(this::convertToDTO);
        }
        List<Product> products = productRepository.findAll(pageable.getSort());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        List<Product> sublist = products.subList(start, end);
        return new PageImpl<>(sublist.stream().map(this::convertToDTO).collect(Collectors.toList()), pageable, products.size());
    }

    // File: `src/main/java/org/example/spring/supermarket/service/impl/ProductServiceImpl.java`
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());

        // Check if the image path is non-null and non-empty before processing.
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            try {
                File file = new File(product.getImage());
                if (file.exists() && file.isFile()) {
                    FileInputStream inputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
                    productDTO.setImage(multipartFile);
                } else {
                    // File does not exist, handle accordingly.
                    productDTO.setImage(null);
                }
            } catch (IOException e) {
                // Handle error accordingly; here we set the image to null if conversion fails.
                productDTO.setImage(null);
            }
        } else {
            productDTO.setImage(null);
        }

        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }
}