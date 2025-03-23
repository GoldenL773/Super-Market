package org.example.spring.supermarket.service;



import org.example.spring.supermarket.dto.CategoryDTO;
import org.example.spring.supermarket.entity.Categories;

import java.util.List;

public interface CategoryService {
    void save(CategoryDTO categoryDTO);
    void update(CategoryDTO categoryDTO);
    void delete(int id);
    CategoryDTO getById(int id);
    Categories getCategories(int id);
    List<CategoryDTO> getAll();
}
