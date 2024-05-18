package com.project.backend.service;

import com.project.backend.model.FlaggedReview;
import com.project.backend.model.Review;
import com.project.backend.model.User;
import com.project.backend.repository.FlaggedReviewRepository;
import com.project.backend.repository.ReviewRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlaggedReviewService {
    private final FlaggedReviewRepository flaggedReviewRepository;
    private final UserRepository userRepository; // to find user by userId
    private final ReviewRepository reviewRepository; // to find review by reviewId

    @Autowired
    public FlaggedReviewService(FlaggedReviewRepository flaggedReviewRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.flaggedReviewRepository = flaggedReviewRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    // get FlaggedReviews flagged by user under the product given
    public List<FlaggedReview> getFlaggedReviewsByUserIdAndProductId(Long userId, Long productId) {
        return flaggedReviewRepository.findAllByUser_UserIdAndReview_OrderItem_Product_ProductId(userId, productId);
    }

    public FlaggedReview toggleFlagByUserIdAndReviewId(Long userId, Long reviewId) {
        // get potential FlaggedReview by UserId and ReviewId - return NULL if not existed
        FlaggedReview flaggedReview = flaggedReviewRepository.findTopByUser_UserIdAndReview_ReviewId(userId, reviewId).orElse(null);

        if (flaggedReview != null) {
            flaggedReviewRepository.delete(flaggedReview); // delete if existed
            return flaggedReview;
        }
        User user = userRepository.findById(userId).orElse(null);
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (user != null && review != null)
            return flaggedReviewRepository.save(new FlaggedReview(null, user, review)); // add if not existed
        return null;
    }
}
