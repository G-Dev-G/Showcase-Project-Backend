package com.project.backend.dto;

public class OrderItemDto {
    private Long orderItemId;
    private ProductDto productDto;
    private Integer quantity;
    private ReviewDto reviewDto; // added property for Frontend

    public OrderItemDto() {}

    public OrderItemDto(Long orderItemId, ProductDto productDto, Integer quantity, ReviewDto reviewDto) {
        this.orderItemId = orderItemId;
        this.productDto = productDto;
        this.quantity = quantity;
        this.reviewDto = reviewDto;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ReviewDto getReviewDto() {
        return reviewDto;
    }

    public void setReviewDto(ReviewDto reviewDto) {
        this.reviewDto = reviewDto;
    }
}
