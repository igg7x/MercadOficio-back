// package com.example.demo.services;

// import java.util.List;

// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.stereotype.Service;

// import com.example.demo.DTO.Review.CreateReviewDTO;
// import com.example.demo.DTO.Review.CreateReviewLikeDTO;
// import com.example.demo.DTO.Review.ReviewDTO;
// import com.example.demo.models.Review;
// import com.example.demo.models.UserCustomer;
// import com.example.demo.models.UserOffering;
// import com.example.demo.repositories.ReviewRepository;
// import com.example.demo.repositories.UserCustomerRepository;
// import com.example.demo.services.mapper.Review.ReviewMapper;

// import jakarta.transaction.Transactional;

// @Service
// public class ReviewService {

// private final ReviewRepository reviewRepository;
// private final UserOfferingService userOfferingService;
// private final UserCustomerService userCustomerService;
// private final ReviewMapper reviewMapper;
// private final UserCustomerRepository userCustomerRepository;

// public ReviewService(ReviewRepository reviewRepository, UserOfferingService
// userOfferingService,
// UserCustomerService userCustomerService, ReviewMapper reviewMapper,
// UserCustomerRepository userCustomerRepository) {
// this.reviewRepository = reviewRepository;
// this.userOfferingService = userOfferingService;
// this.userCustomerService = userCustomerService;
// this.reviewMapper = reviewMapper;
// this.userCustomerRepository = userCustomerRepository;
// }

// public Page<ReviewDTO> getReviewsByUserOffering(String userEmailReviewed,
// Pageable pageable) {

// UserOffering userOffering =
// userOfferingService.getUserOffering(userEmailReviewed);

// Page<Review> reviews =
// reviewRepository.findByUserOfferingAndDeletedAtIsNull(userOffering,
// pageable);

// List<ReviewDTO> reviewDTOList =
// reviewMapper.ReviewListToReviewDTOList(reviews.getContent(),
// userEmailReviewed);

// return new PageImpl<>(reviewDTOList, pageable, reviews.getTotalElements());

// }

// @Transactional
// public ReviewDTO createReview(CreateReviewDTO createReviewDTO) {

// UserCustomer userCustomer =
// userCustomerService.getUserCustomer(createReviewDTO.getUserEmailReviewer());
// UserOffering userOffering =
// userOfferingService.getUserOffering(createReviewDTO.getUserEmailReviewed());

// Review reviewCreated = reviewMapper.CreateReviewDTOtoReview(createReviewDTO,
// userCustomer, userOffering);
// ;

// reviewCreated = reviewRepository.save(reviewCreated);

// return reviewMapper.ReviewtoReviewDTO(reviewCreated,
// createReviewDTO.getUserEmailReviewer(),
// createReviewDTO.getUserEmailReviewed());
// }

// @Transactional
// public ReviewDTO createReviewLike(CreateReviewLikeDTO createReviewLikeDTO) {

// UserCustomer userCustomer =
// userCustomerService.getUserCustomer(createReviewLikeDTO.getEmail());

// Review review =
// reviewRepository.findById(createReviewLikeDTO.getReviewId()).get();

// List<UserCustomer> reviewsLikes = review.getReviewLikes();
// reviewsLikes.add(userCustomer);
// review.setReviewLikes(reviewsLikes);

// List<Review> userLikes = userCustomer.getUserCustomerLikes();
// userLikes.add(review);
// userCustomer.setUserCustomerLikes(userLikes);

// review = reviewRepository.save(review);
// userCustomerRepository.save(userCustomer);

// return reviewMapper.ReviewtoReviewDTO(review,
// createReviewLikeDTO.getEmail());
// }

// @Transactional
// public ReviewDTO deleteReviewLike(CreateReviewLikeDTO createReviewDTO) {

// UserCustomer userCustomer =
// userCustomerService.getUserCustomer(createReviewDTO.getEmail());

// Review review =
// reviewRepository.findById(createReviewDTO.getReviewId()).get();

// List<UserCustomer> reviewsLikes = review.getReviewLikes();
// reviewsLikes.remove(userCustomer);
// review.setReviewLikes(reviewsLikes);

// List<Review> userLikes = userCustomer.getUserCustomerLikes();
// userLikes.remove(review);
// userCustomer.setUserCustomerLikes(userLikes);

// review = reviewRepository.save(review);

// return reviewMapper.ReviewtoReviewDTO(review, createReviewDTO.getEmail());
// }

// @Transactional
// public void deleteReview() {
// // THIS METHOD WILL BE IMPLEMENTED WHEN THE MODULE OF SECURYTY IS IMPLEMENTED

// }

// public boolean existsReviewLike(CreateReviewLikeDTO createReviewLikeDTO) {
// UserCustomer userCustomer =
// userCustomerService.getUserCustomer(createReviewLikeDTO.getEmail());
// return
// reviewRepository.existsByReviewIdAndReviewLikesUserCustomerId(createReviewLikeDTO.getReviewId(),
// userCustomer.getUserCustomerId());
// }

// }
