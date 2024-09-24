package com.example.demo.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Job;
import com.example.demo.models.Review;
import com.example.demo.models.User;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.services.mapper.Review.ReviewMapper;
import com.example.demo.services.specifications.ReviewSpecifications;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;
    private final JobService jobService;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserService userService,
            JobService jobService) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userService = userService;
        this.jobService = jobService;
    }

    public Page<ReviewDTO> getReviewsByUserReviewed(String userEmailReviewed,
            Pageable pageable) {
        User user = userService.findByEmail(userEmailReviewed);
        Page<Review> reviews = reviewRepository
                .findAll(ReviewSpecifications.findByUserReviewedIdAndDeletedIsNull(user.getUserId()), pageable);

        Page<ReviewDTO> reviewsDTOPage = reviews.map(
                review -> reviewMapper.ReviewtoReviewDTO(review));
        return reviewsDTOPage;
    }

    @Transactional
    public ReviewDTO createReview(CreateReviewDTO createReviewDTO) {

        User userReviewer = userService.findByEmail(createReviewDTO.getUserEmailReviewer());

        User userReviewed = userService.findByEmail(createReviewDTO.getUserEmailReviewed());

        Job jobToReview = jobService.findJobById(createReviewDTO.getJobId());
        Review reviewCreated = reviewMapper.CreateReviewDTOtoReview(createReviewDTO, jobToReview, userReviewed,
                userReviewer);
        ;

        reviewCreated = reviewRepository.save(reviewCreated);

        return reviewMapper.ReviewtoReviewDTO(reviewCreated);
    }

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

}
