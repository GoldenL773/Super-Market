package org.example.spring.supermarket.service.impl;


import org.example.spring.supermarket.DTO.CategoryDTO;
import org.example.spring.supermarket.entity.Categories;
import org.example.spring.supermarket.repository.CategoryRepository;
import org.example.spring.supermarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        Categories categories = new Categories();
        categories.setName(categoryDTO.getName());
        categories.setImage(categoryDTO.getImage());
        categories.setDescription(categoryDTO.getDescription());
        categoryRepository.save(categories);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Categories categories = categoryRepository.getReferenceById(categoryDTO.getId());
        categories.setName(categoryDTO.getName());
        categories.setImage(categoryDTO.getImage());
        categories.setDescription(categoryDTO.getDescription());
        categoryRepository.save(categories);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getById(int id) {
        return categoryRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public Categories getCategories(int id) {
        return categoryRepository.findById(id).orElse(null);
    }


    @Override
    public List<CategoryDTO> getAll() {
        List<Categories> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Categories category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setImage(category.getImage());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }
}
