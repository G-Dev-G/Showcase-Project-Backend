package com.project.backend.dto;

public class ShoppingCartDto {
    private Long shoppingCartId;
    private ProductDto productDto;
    private Long userId;
    private Integer quantity;
    private Boolean checked;

    public ShoppingCartDto() {
    }

    public ShoppingCartDto(Long shoppingCartId, ProductDto productDto, Long userId, Integer quantity, Boolean checked) {
        this.shoppingCartId = shoppingCartId;
        this.productDto = productDto;
        this.userId = userId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
