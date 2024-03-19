package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryId(Long categoryId);

    Category findByCategoryName(String categoryName);

    // List<Category> findByUserOfferings(UserOffering userOffering);

    // @Query("SELECT u.userCategories FROM UserOffering u WHERE u.userOfferingId
    // =\r\n" + //
    // " // :userOfferingId AND c.categoryId = :categoryId")
    // Optional<Category> findCategoryIfExist(@Param("categoryId") Long categoryId,
    // @Param("userOfferingId") Long userOfferingId);

    // @Query("SELECT u.userCategories FROM UserOffering u WHERE u.userOfferingId =
    // :userOfferingId")
    // List<Category> findCategoriesByUserOfferingId(@Param("userOfferingId") Long
    // userOfferingId);
}
