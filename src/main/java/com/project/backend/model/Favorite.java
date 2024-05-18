package com.project.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "Favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long favoriteId; // primary key

    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product; // foreign key 1

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user; // foreign key 2

    public Favorite() {}

    public Favorite(Long favoriteId, Product product, User user) {
        this.favoriteId = favoriteId;
        this.product = product;
        this.user = user;
    }

    public Long getFavoriteId() {
        return favoriteId;
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
}
