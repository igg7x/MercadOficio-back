package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserCategories;
import com.example.demo.models.UserCategoriesKey;

@Repository
public interface UserCategoriesRepository extends JpaRepository<UserCategories, UserCategoriesKey> {

    // Optional<User> findByUserIdAndCategoryId(User user, Category category);

    // Optional<List<UserCategories>> findByUserOfferingId(Long userOfferingId);

    // Optional<List<UserCategories>> findByCategoryId(Long categoryId);

}
