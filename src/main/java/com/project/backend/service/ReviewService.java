package com.project.backend.service;

import com.project.backend.dto.ReviewDto;
import com.project.backend.model.FlaggedReview;
import com.project.backend.model.OrderItem;
import com.project.backend.model.Review;
import com.project.backend.repository.FlaggedReviewRepository;
import com.project.backend.repository.OrderItemRepository;
import com.project.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository; // to find orderItem the review belongs to
    private final FlaggedReviewRepository flaggedReviewRepository; // to get flag count

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, OrderItemRepository orderItemRepository, FlaggedReviewRepository flaggedReviewRepository) {
        this.reviewRepository = reviewRepository;
        this.orderItemRepository = orderItemRepository;
        this.flaggedReviewRepository = flaggedReviewRepository;
    }

    // Get potential review for an order item - return NULL if not exist
    public Review getReviewByOrderItemId(Long orderItemId) {
        Review review = reviewRepository.findTopByOrderItem_OrderItemId(orderItemId).orElse(null);
        return review;
    }

    // Get reviews for a product
    public List<Review> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductIdOrderByOrderItemIdDesc(productId);
        return reviews;
    }

    // Set (Add or Update) review
    public Review setReview(Review review) {
        Review reviewToDelete = getReviewByOrderItemId(review.getOrderItem().getOrderItemId());
        // delete potential review first
        if (reviewToDelete != null)
            reviewRepository.delete(reviewToDelete);
        return reviewRepository.save(review);
    }


    /**
     * Entity to Dto conversion
     * @param review
     * @return reviewDto
     */
    public ReviewDto reviewEntityToDto(Review review) {
        if (review != null) {
            // get total flags of the review
            List<FlaggedReview> flaggedReviewList = flaggedReviewRepository.findAllByReview_ReviewId(review.getReviewId());

            ReviewDto reviewDto = new ReviewDto(
                    review.getReviewId(),
                    review.getOrderItem().getOrderItemId(),
                    review.getRating(),
                    review.getComment(),
                    review.getReviewDate(),
                    review.getOrderItem().getOrder().getUser().getName(),
                    flaggedReviewList.size()
            );
            return reviewDto;
        }
        return null;
    }

    /**
     * Dto to Entity conversion
     * @param reviewDto
     * @return review
     */
    public Review reviewDtoToEntity(ReviewDto reviewDto) {
        if (reviewDto != null) {
            // get its orderItem by id
            OrderItem orderItem = orderItemRepository.findById(reviewDto.getOrderItemId()).orElse(null);
            if (orderItem != null) {
                Review review = new Review(
                        reviewDto.getReviewId(),
                        orderItem,
                        reviewDto.getRating(),
                        reviewDto.getComment(),
                        reviewDto.getReviewDate()
                );
                return review;
            }
        }
        return null;
    }
}
