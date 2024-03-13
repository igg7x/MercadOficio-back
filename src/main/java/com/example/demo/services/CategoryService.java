package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

            Category cat = categoryRepository.findByCategoryName(category.getName());
            if (cat == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category not found");
            }
            categories.add(cat);
        }
        return categories;
    }

}
