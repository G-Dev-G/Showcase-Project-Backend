package com.project.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "ProductImage")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long productImageId;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product; // foreign key

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    public ProductImage() {
    }

    public ProductImage(Product product, byte[] image) {
        this.product = product;
        this.image = image;
    }

    public Long getProductImageId() {
        return productImageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
