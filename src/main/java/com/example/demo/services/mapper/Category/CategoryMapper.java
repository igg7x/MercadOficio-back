package com.example.demo.services.mapper.Category;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.models.Category;

@Mapper
public interface CategoryMapper {

    List<CategorieDTO> CategoryListtoCategoryDTOList(List<Category> userCategories);

}
