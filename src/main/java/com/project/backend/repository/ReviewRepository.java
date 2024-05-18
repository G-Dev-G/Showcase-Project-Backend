package com.project.backend.repository;

import com.project.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Find all reviews for the product
    @Query(value = "SELECT r FROM Review r INNER JOIN r.orderItem i INNER JOIN i.product p WHERE p.productId = :productId ORDER BY i.orderItemId DESC")
    List<Review> findAllByProductIdOrderByOrderItemIdDesc(@Param("productId") Long productId);

    // Find review for the individual order item
    Optional<Review> findTopByOrderItem_OrderItemId(Long orderItemId);
}
