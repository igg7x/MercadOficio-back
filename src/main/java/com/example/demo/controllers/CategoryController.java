package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.Categories.CategorieDataDTO;
import com.example.demo.DTO.Categories.UpdateCategoryStatusDTO;
import com.example.demo.auth.CurrentUserEmail;
import com.example.demo.models.Category;
import com.example.demo.services.CategoryService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(@RequestBody List<CategorieDTO> userCategories) {
        return ResponseEntity.ok(categoryService.getAllCategories(userCategories));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {

        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/all/admin/data")
    public ResponseEntity<List<CategorieDataDTO>> getAllCategoriesData(@CurrentUserEmail String email) {
        return ResponseEntity.ok(categoryService.getCategoriesData(email));
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> addCategory(@Validated @RequestBody CategorieDTO category) {
        try {
            CategorieDTO newCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(Map.of(
                    "message", "Categoría agregada correctamente",
                    "category", newCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al agregar la categoría",
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PatchMapping("/admin/update-status")
    public ResponseEntity<?> updateCategoryStatus(@Validated @RequestBody UpdateCategoryStatusDTO category) {
        try {
            CategorieDTO updatedCategory = categoryService.updateCategoryStatus(category);
            return ResponseEntity.ok(Map.of(
                    "message", "Categoría actualizada correctamente",
                    "category", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al actualizar la categoría",
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
