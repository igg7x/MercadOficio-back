package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.models.Category;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.UserOfferingRepository;
import com.example.demo.services.mapper.Category.CategoryMapper;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserOfferingRepository userOfferingRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
            UserOfferingRepository userOfferingRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.userOfferingRepository = userOfferingRepository;
    }

    public List<CategorieDTO> getCategories() {
        return categoryMapper.CategoryListtoCategoryDTOList(categoryRepository.findAll());
    }

    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByCategoryName(name);
        if (category == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return category;
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

    public List<CategorieDTO> getCategoriesFromUser(UserOffering userOffering) {
        List<Category> categories = userOfferingRepository
                .findCategoriesByUserOfferingId(userOffering.getUserOfferingId());
        return categoryMapper.CategoryListtoCategoryDTOList(categories);
    }

    public void updateCategories(UpdateUserOfferingDTO updateUserOfferingDTO, UserOffering userOffering) {
        if (updateUserOfferingDTO.getCategories() != null) {

            if (!updateUserOfferingDTO.getCategories().isEmpty()) {

                // for (CategorieDTO categorieDTO : updateUserOfferingDTO.getCategories()) {

                // Category category =
                // categoryRepository.findByCategoryName(categorieDTO.getName());

                // if (category == null) {
                // throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category not
                // found");
                // }

                // if
                // (!userOfferingRepository.existsByUserOfferingIdAndCategoryId(userOffering.getUserOfferingId(),
                // category.getCategoryId())) {

                // userOffering.getUserCategories().add(category);
                // }
                // }
                userOffering.setUserCategories(
                        categoryMapper.CategoryDTOListtoCategoryList(updateUserOfferingDTO.getCategories()));
            }
        }
    }

}
