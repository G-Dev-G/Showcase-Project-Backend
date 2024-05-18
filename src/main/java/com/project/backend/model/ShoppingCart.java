package com.project.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "ShoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long shoppingCartId; // primary key

    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product; // foreign key 1

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user; // foreign key 2

    private Integer quantity;
    private Boolean checked;

    public ShoppingCart() {
    }

    public ShoppingCart(Long shoppingCartId, Product product, User user, Integer quantity, Boolean checked) {
        this.shoppingCartId = shoppingCartId;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
