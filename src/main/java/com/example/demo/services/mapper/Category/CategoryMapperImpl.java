package com.example.demo.services.mapper.Category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.models.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    public List<CategorieDTO> CategoryListtoCategoryDTOList(List<Category> categories) {

        List<CategorieDTO> categorieDTOs = new ArrayList<>();
        for (Category category : categories) {
            CategorieDTO categorieDTO = new CategorieDTO();
            categorieDTO.setName(category.getCategoryName());
            categorieDTOs.add(categorieDTO);
        }

        return categorieDTOs;
    }

    @Override
    public List<Category> CategoryDTOListToCategoryList(List<CategorieDTO> categories) {

        List<Category> categoryList = new ArrayList<>();
        for (CategorieDTO categorieDTO : categories) {
            Category category = new Category();
            category.setCategoryName(categorieDTO.getName());
            categoryList.add(category);
        }
        return categoryList;
    }

    @Override
    public CategorieDTO CategorytoCategoryDTO(Category category) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setName(category.getCategoryName());
        return categorieDTO;
    }
}
