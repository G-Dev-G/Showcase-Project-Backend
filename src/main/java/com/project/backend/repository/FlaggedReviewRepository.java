package com.project.backend.repository;

import com.project.backend.model.FlaggedReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlaggedReviewRepository extends JpaRepository<FlaggedReview, Long> {
    // Find potential flagged review based on userId and reviewId
    Optional<FlaggedReview> findTopByUser_UserIdAndReview_ReviewId(Long userId, Long reviewId);

    // Find flagged reviews based on userId and productId
    List<FlaggedReview> findAllByUser_UserIdAndReview_OrderItem_Product_ProductId(Long userId, Long productId);

    // Find All based on reviewId
    List<FlaggedReview> findAllByReview_ReviewId(Long reviewId);
}
