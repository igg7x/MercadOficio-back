package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Review;
import com.example.demo.models.UserOffering;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewIdAndDeletedAtIsNotNull(Long reviewId);

    List<Review> findByUserOffering(UserOffering userOffering);

    // Optional<List<Review>> findByUserOfferingIdAndDeletedAtIsNotNull(Long
    // userOfferingId);

    // Optional<List<Review>> findByUserCustomerIdAndDeletedAtIsNotNull(Long
    // userCustomerId);

}
