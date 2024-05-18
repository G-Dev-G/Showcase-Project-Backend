package com.project.backend.dto;

import java.util.List;

public class ProductDto {
    private Long productId;
    private String shortName;
    private String fullName;
    private String description;
    private Float price;
    private String category;
    private List<byte[]> images;

    public ProductDto() {}

    public ProductDto(Long productId, String shortName, String fullName, String description, Float price, String category, List<byte[]> images) {
        this.productId = productId;
        this.shortName = shortName;
        this.fullName = fullName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.images = images;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
