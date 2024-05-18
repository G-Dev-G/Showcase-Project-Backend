package com.project.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "OrderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long orderItemId; // primary key

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order; // foreign key 1

    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product; // foreign key 2

    private Integer quantity;

    public OrderItem() {}

    public OrderItem(Long orderItemId, Order order, Product product, Integer quantity) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
