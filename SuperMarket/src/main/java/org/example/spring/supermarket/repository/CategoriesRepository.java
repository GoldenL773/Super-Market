package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}
