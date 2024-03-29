package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Review;
import com.example.demo.models.UserOffering;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewIdAndDeletedAtIsNull(Long reviewId);

    Page<Review> findByUserOfferingAndDeletedAtIsNull(UserOffering userOffering, Pageable pageable);

    // Optional<List<Review>> findByUserOfferingIdAndDeletedAtIsNotNull(Long
    // userOfferingId);

    // Optional<List<Review>> findByUserCustomerIdAndDeletedAtIsNotNull(Long
    // userCustomerId);
    // @Query("SELECT c.userCustomerId FROM customer_reviews_likes c WHERE
    // c.review_id = :reviewId AND c.user_customer_id = :userCustomerId")
    // @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM
    // CustomerReviewLikes c WHERE c.reviewId = :reviewId AND c.userCustomerId =
    // :userCustomerId")
    // boolean existsLikeByReviewIdAndUserCustomerId(@Param("reviewId") Long
    // reviewId,
    // @Param("userCustomerId") Long userCustomerId);
    boolean existsByReviewIdAndReviewLikesUserCustomerId(Long reviewId, Long userCustomerId);

}
