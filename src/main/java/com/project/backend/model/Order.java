package com.project.backend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "UserOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long orderId; // primary key

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user; // foreign key

    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    private Date orderDate;

    private String userFullName;
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private OrderStatus statusCheckedByUserLastTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus statusCheckedByAdminLastTime; // NULL at first

    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt; // timestamp - Deletion flag

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList; // has many

    public Order() {}

    public Order(Long orderId, User user, Date orderDate, String userFullName, String address, OrderStatus status, OrderStatus statusCheckedByUserLastTime) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.userFullName = userFullName;
        this.address = address;
        this.status = status;
        this.statusCheckedByUserLastTime = statusCheckedByUserLastTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserFullName() { return userFullName; }

    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatusCheckedByUserLastTime() {
        return statusCheckedByUserLastTime;
    }

    public void setStatusCheckedByUserLastTime(OrderStatus statusCheckedByUserLastTime) {
        this.statusCheckedByUserLastTime = statusCheckedByUserLastTime;
    }

    public OrderStatus getStatusCheckedByAdminLastTime() {
        return statusCheckedByAdminLastTime;
    }

    public void setStatusCheckedByAdminLastTime(OrderStatus statusCheckedByAdminLastTime) {
        this.statusCheckedByAdminLastTime = statusCheckedByAdminLastTime;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
