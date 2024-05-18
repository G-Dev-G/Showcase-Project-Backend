package com.project.backend.controller;

import com.project.backend.dto.ReviewDto;
import com.project.backend.model.Review;
import com.project.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getAllByProductId/{productId}")
    public List<ReviewDto> getAllReviewsByProductId(@PathVariable(value="productId") Long productId) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for (Review review : reviewService.getReviewsByProductId(productId)) {
            reviewDtoList.add(reviewService.reviewEntityToDto(review));
        }
        return reviewDtoList;
    }

    @PutMapping("/set")
    public ReviewDto setReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewService.reviewDtoToEntity(reviewDto);
        Review reviewSet = reviewService.setReview(review);
        return reviewService.reviewEntityToDto(reviewSet); // always return DTO
    }
}
