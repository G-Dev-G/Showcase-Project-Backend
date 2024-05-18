package com.project.backend.dto;

public class FavoriteDto {
    private Long favoriteId;
    private ProductDto productDto;
    private Long userId;

    public FavoriteDto() {}

    public FavoriteDto(Long favoriteId, ProductDto productDto, Long userId) {
        this.favoriteId = favoriteId;
        this.productDto = productDto;
        this.userId = userId;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
