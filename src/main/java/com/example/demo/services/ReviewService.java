package com.example.demo.services;

import org.springframework.data.domain.Pageable;

import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.DeleteReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.Review.UpdateReviewDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.Exceptions.JobNotFoundException;
import com.example.demo.Exceptions.ReviewExistsException;
import com.example.demo.models.Job;
import com.example.demo.models.Notification;
import com.example.demo.models.Review;
import com.example.demo.models.User;
import com.example.demo.models.Notification.TypesNotification;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.services.mapper.Review.ReviewMapper;
import com.example.demo.services.specifications.ReviewSpecifications;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewService {

        private final ReviewRepository reviewRepository;
        private final ReviewMapper reviewMapper;
        private final UserService userService;
        private final JobService jobService;
        private final NotificationService notificationService;
        private final UserOfferingService userOfferingService;

        public Page<ReviewDTO> getReviews(String userEmailReviewer,
                        Pageable pageable, String searchFilter) {
                User user = userService.findByEmail(userEmailReviewer);
                Page<Review> reviews = reviewRepository
                                .findAll(ReviewSpecifications.findReviews(user.getUserId(), searchFilter),
                                                pageable);

                Page<ReviewDTO> reviewsDTOPage = reviews.map(
                                review -> reviewMapper.ReviewtoReviewDTO(review));
                return reviewsDTOPage;
        }

        @Transactional
        public ReviewDTO createReview(CreateReviewDTO createReviewDTO) {

                User userReviewer = userService.findByEmail(createReviewDTO.getUserEmailReviewer());

                User userReviewed = userService.findByEmail(createReviewDTO.getUserEmailReviewed());

                Job jobToReview = jobService.findJobById(createReviewDTO.getJobId());

                if (userReviewer.getUserId().equals(userReviewed.getUserId())) {
                        throw new org.springframework.web.client.HttpClientErrorException(
                                        org.springframework.http.HttpStatus.BAD_REQUEST,
                                        "User can't review himself");
                }

                if (reviewRepository.findOne(ReviewSpecifications.findByUserReviewerAndUserReviewedAndJobId(
                                userReviewer.getUserId(), userReviewed.getUserId(), jobToReview.getJobId()))
                                .isPresent()) {
                        throw new ReviewExistsException("La reseña ya existe");
                }

                Review reviewCreated = createAndSaveReview(createReviewDTO, jobToReview,
                                userReviewed, userReviewer);

                if (jobToReview.getUserCustomer().getUser().getUserId().equals(userReviewer.getUserId())) {
                        UpdateUserOfferingDTO updateUserOfferingDTO = new UpdateUserOfferingDTO();

                        updateUserOfferingDTO.setCalification(createReviewDTO.getRating());

                        userOfferingService.updateUserOffering(userReviewed.getEmail(), updateUserOfferingDTO);
                }

                notifyUserReview(userReviewer, userReviewed, jobToReview);
                return reviewMapper.ReviewtoReviewDTO(reviewCreated);
        }

        private void notifyUserReview(User userReviewer, User userReviewed, Job jobToReview) {

                String reviewMessage = String.format("El usuario: %s te ha reseñado en el trabajo: %s",
                                userReviewer.getEmail(), jobToReview.getTitle());

                // emailService.sendEmail(userReviewed.getEmail(), "Te han Reseñado !",
                // reviewMessage);

                Notification notificationReview = notificationService.createNotification("Te han Reseñado !",
                                TypesNotification.INFO, reviewMessage);

                notificationService.sendNotifications(Collections.singletonList(userReviewed.getEmail()),
                                notificationReview, null);
                ;

        }

        private Review createAndSaveReview(CreateReviewDTO createReviewDTO, Job jobToReview, User userReviewed,
                        User userReviewer) {
                Review reviewCreated = reviewMapper.CreateReviewDTOtoReview(createReviewDTO, jobToReview, userReviewed,
                                userReviewer);
                ;
                return reviewRepository.save(reviewCreated);
        }

        public Integer getReviewCount(User user) {
                Long count = reviewRepository
                                .count(ReviewSpecifications.findByUserReviewedIdAndDeletedIsNull(user.getUserId()));

                return count.intValue();
        }

        @Transactional
        public ReviewDTO updateReview(UpdateReviewDTO updateReviewDTO) {
                User userReviewed = userService.findByEmail(updateReviewDTO.getUserReviewedEmail());
                User userReviwer = userService.findByEmail(updateReviewDTO.getUserReviewerEmail());
                Job job = jobService.findJobById(updateReviewDTO.getJobId());

                Review review = reviewRepository
                                .findOne(ReviewSpecifications.findByUserReviewerAndUserReviewedAndJobId(
                                                userReviwer.getUserId(), userReviewed.getUserId(), job.getJobId()))
                                .orElseThrow(() -> new JobNotFoundException("Reseña no encontrada"));
                Review reviewUpdated = reviewMapper.updateReview(review, updateReviewDTO);
                return reviewMapper.ReviewtoReviewDTO(reviewRepository.save(reviewUpdated));

        }

        @Transactional
        public void deleteReview(DeleteReviewDTO deleteReviewDTO) {
                User userReviewed = userService.findByEmail(deleteReviewDTO.getUserReviewedEmail());
                User userReviwer = userService.findByEmail(deleteReviewDTO.getUserReviewerEmail());
                Job job = jobService.findJobById(deleteReviewDTO.getJobId());

                Review review = reviewRepository
                                .findOne(ReviewSpecifications.findByUserReviewerAndUserReviewedAndJobId(
                                                userReviwer.getUserId(), userReviewed.getUserId(), job.getJobId()))
                                .orElseThrow(() -> new JobNotFoundException("Reseña no encontrada"));

                review.setDeleted_at(java.time.LocalDate.now());
                reviewRepository.save(review);
                notifyDeletingReview(userReviwer, userReviewed, job);
        }

        private void notifyDeletingReview(User userReviewer, User userReviewed, Job jobToReview) {
                String reviewMessage = String.format("El usuario: %s ha eliminado su reseña en el trabajo: %s",
                                userReviewer.getEmail(), jobToReview.getTitle());

                // emailService.sendEmail(userReviewed.getEmail(), "Te han Reseñado !",
                // reviewMessage);

                Notification notificationReview = notificationService.createNotification("Se ha eliminado una reseña !",
                                TypesNotification.INFO, reviewMessage);

                notificationService.sendNotifications(Collections.singletonList(userReviewed.getEmail()),
                                notificationReview, null);
                ;
        }

}
