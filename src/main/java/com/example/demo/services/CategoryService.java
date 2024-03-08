package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(List<CategorieDTO> userCategories) {
        List<Category> categories = new ArrayList<>();
        for (CategorieDTO category : userCategories) {
            categories.add(categoryRepository.findByCategoryName(category.getName()).get());
        }
        return categories;
    }

}
