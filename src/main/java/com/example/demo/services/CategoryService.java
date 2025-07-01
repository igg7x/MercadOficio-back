package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.Categories.CategorieDataDTO;
import com.example.demo.DTO.Categories.UpdateCategoryStatusDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.Exceptions.CategoryNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.mapper.Category.CategoryMapper;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Lazy
    @Autowired
    private UserOfferingService userOfferingService;

    @Lazy
    @Autowired
    private JobService jobService;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategorieDTO> getCategories() {

        return categoryMapper.CategoryListtoCategoryDTOList(categoryRepository.findAllByCategoryStatusFalse());
    }

    // public List<CategorieDTO> getCategoriesByStatus(Boolean status) {
    // return
    // categoryMapper.CategoryListtoCategoryDTOList(categoryRepository.findByCategoryStatusIsFalse(status));
    // }

    public List<CategorieDataDTO> getCategoriesData(String admin_email) {
        // falta checkear si el usuario es admin
        List<CategorieDataDTO> categoriesData = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            CategorieDataDTO categorieDataDTO = new CategorieDataDTO();
            categorieDataDTO.setCategory_name(category.getCategoryName());
            categorieDataDTO.setCategory_count(userOfferingService.getUsersCountByCategory(category.getCategoryName()));
            categorieDataDTO.setActive_jobs_count(jobService.getJobsCountByCategory(category.getCategoryName()));
            categorieDataDTO.setCategory_status(category.getCategoryStatus());
            categoriesData.add(categorieDataDTO);
        }
        return categoriesData;
    }

    public Category getCategory(String name) {
        Category category = categoryRepository.findByCategoryNameAndCategoryStatusIsFalse(name);
        if (category == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return category;
    }

    public List<Category> getAllCategories(List<CategorieDTO> categoriesDTOs) {
        List<Category> categories = new ArrayList<>();
        for (CategorieDTO category : categoriesDTOs) {
            Category cat = categoryRepository.findByCategoryNameAndCategoryStatusIsFalse(category.getName());
            if (cat == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category not found");
            }
            categories.add(cat);
        }
        return categories;
    }

    public List<CategorieDTO> getCategoriesFromUser(UserOffering userOffering) {
        List<Category> categories = userOfferingService
                .getCategoriesByUserOfferingId(userOffering.getUserOfferingId());
        return categoryMapper.CategoryListtoCategoryDTOList(categories);
    }

    @Transactional
    public void updateCategories(UpdateUserOfferingDTO updateUserOfferingDTO, UserOffering userOffering) {
        if (updateUserOfferingDTO.getCategories() != null && !updateUserOfferingDTO.getCategories().isEmpty()) {
            List<Category> categories = new ArrayList<>();
            for (CategorieDTO categoryDTO : updateUserOfferingDTO.getCategories()) {
                Category category = categoryRepository
                        .findByCategoryNameAndCategoryStatusIsFalse(categoryDTO.getName());
                categories.add(category);
            }
            userOffering.setUserCategories(categories);
        }
    }

    @Transactional
    public CategorieDTO updateCategoryStatus(UpdateCategoryStatusDTO updateCategoryStatusDTO) {
        Category category = categoryRepository.findByCategoryName(updateCategoryStatusDTO.getCategory_name());
        if (category == null) {
            throw new CategoryNotFoundException("Categoria no encontrada");
        }
        category.setCategoryStatus(updateCategoryStatusDTO.getCategory_status());
        categoryRepository.save(category);
        return categoryMapper.CategorytoCategoryDTO(category);
    }

    public CategorieDTO addCategory(CategorieDTO category) {
        Category newCategory = categoryMapper.CategoryDTOtoCategory(category);
        categoryRepository.save(newCategory);
        return categoryMapper.CategorytoCategoryDTO(newCategory);

    }

    public long getTotalCategories() {
        return categoryRepository.count();
    }

    public long getTotalActiveCategories() {
        return categoryRepository.countByCategoryStatusIsFalse();
    }

    public long getTotalActiveJobs() {
        return jobService.countTotalActiveJobs();
    }
}
