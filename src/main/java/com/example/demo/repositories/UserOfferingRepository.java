package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;

@Repository
public interface UserOfferingRepository extends JpaRepository<UserOffering, Long> {
    Optional<UserOffering> findByUser(User user);

    @Query("SELECT u.userCategories FROM UserOffering u WHERE u.userOfferingId = :userOfferingId")
    List<Category> findCategoriesByUserOfferingId(@Param("userOfferingId") Long userOfferingId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserOffering u JOIN u.userCategories c WHERE u.userOfferingId = :userOfferingId AND c.categoryId = :categoryId")
    boolean existsByUserOfferingIdAndCategoryId(@Param("userOfferingId") Long userOfferingId,
            @Param("categoryId") Long categoryId);

}
