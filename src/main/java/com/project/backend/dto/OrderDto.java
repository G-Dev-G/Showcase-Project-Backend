package com.project.backend.dto;

import java.util.Date;
import java.util.List;

public class OrderDto {
    private Long orderId;
    private UserDto userDto;
    private Date orderDate;
    private String userFullName;
    private String address;
    private String status;
    private String statusCheckedByUserLastTime;
    private String statusCheckedByAdminLastTime;
    private List<OrderItemDto> orderItemDtoList;

    public OrderDto() {}

    public OrderDto(Long orderId, UserDto userDto, Date orderDate, String userFullName, String address, String status, String statusCheckedByUserLastTime, String statusCheckedByAdminLastTime, List<OrderItemDto> orderItemDtoList) {
        this.orderId = orderId;
        this.userDto = userDto;
        this.orderDate = orderDate;
        this.userFullName = userFullName;
        this.address = address;
        this.status = status;
        this.statusCheckedByUserLastTime = statusCheckedByUserLastTime;
        this.statusCheckedByAdminLastTime = statusCheckedByAdminLastTime;
        this.orderItemDtoList = orderItemDtoList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCheckedByUserLastTime() {
        return statusCheckedByUserLastTime;
    }

    public void setStatusCheckedByUserLastTime(String statusCheckedByUserLastTime) {
        this.statusCheckedByUserLastTime = statusCheckedByUserLastTime;
    }

    public String getStatusCheckedByAdminLastTime() {
        return statusCheckedByAdminLastTime;
    }

    public void setStatusCheckedByAdminLastTime(String statusCheckedByAdminLastTime) {
        this.statusCheckedByAdminLastTime = statusCheckedByAdminLastTime;
    }

    public List<OrderItemDto> getOrderItemDtoList() {
        return orderItemDtoList;
    }

    public void setOrderItemDtoList(List<OrderItemDto> orderItemDtoList) {
        this.orderItemDtoList = orderItemDtoList;
    }
}
