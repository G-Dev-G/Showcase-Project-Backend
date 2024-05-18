package com.project.backend.service;

import com.project.backend.dto.OrderDto;
import com.project.backend.dto.OrderItemDto;
import com.project.backend.model.*;
import com.project.backend.repository.OrderRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository; // to find user by userId
    private final ReviewService reviewService; // to find review of the order item in order

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, ReviewService reviewService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.reviewService = reviewService;
    }

    // Retrieve order By Id
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // Retrieve all existing orders (for USER)
    public List<Order> getAllExistingOrdersByUserId(Long userId) {
        return orderRepository.findAllExistingByUser_UserIdOrderByOrderIdDesc(userId);
    }

    // Retrieve all orders (for ADMIN)
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderIdDesc();
    }

    // Add Order
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    // Update order status - for user operation
    public Order updateOrderStatusUser(Long orderId, String status) {
        Order orderToUpdate = getOrderById(orderId);
        if (orderToUpdate != null) {
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase()); // catch exception
                // set status and status checked last time
                orderToUpdate.setStatus(orderStatus);
                orderToUpdate.setStatusCheckedByUserLastTime(orderStatus);
                // save
                return orderRepository.save(orderToUpdate);
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    // Update order status - for admin operation
    public Order updateOrderStatusAdmin(Long orderId, String status) {
        Order orderToUpdate = getOrderById(orderId);
        if (orderToUpdate != null) {
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase()); // catch exception
                // set status and status checked last time
                orderToUpdate.setStatus(orderStatus);
                orderToUpdate.setStatusCheckedByAdminLastTime(orderStatus);
                // save
                return orderRepository.save(orderToUpdate);
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    // Delete Order By setting deletedAt
    public Order deleteOrder(Long id) {
        Order orderToDelete = orderRepository.findById(id).orElse(null);

        if (orderToDelete != null) {
            orderToDelete.setDeletedAt(new Date());
            return orderRepository.save(orderToDelete);
        }
        return null;
    }


    /**
     * Entity to Dto conversion
     * @param order
     * @return orderDto
     */
    public OrderDto orderEntityToDto(Order order) {
        if (order != null) {
            // construct orderItemDto list
            List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            for (int i = 0; i < order.getOrderItemList().size(); i++) {
                orderItemDtoList.add(new OrderItemDto(
                                order.getOrderItemList().get(i).getOrderItemId(),
                                productService.productEntityToDto(order.getOrderItemList().get(i).getProduct()),
                                order.getOrderItemList().get(i).getQuantity(),
                                reviewService.reviewEntityToDto(
                                        // get potential review of orderItemDto
                                        reviewService.getReviewByOrderItemId(order.getOrderItemList().get(i).getOrderItemId())
                                )
                        )
                );
            }
            OrderDto orderDto = new OrderDto(
                    order.getOrderId(),
                    customUserDetailsService.userEntityToDto(order.getUser()),
                    order.getOrderDate(),
                    order.getUserFullName(),
                    order.getAddress(),
                    order.getStatus().name(),
                    order.getStatusCheckedByUserLastTime() == null ? null : order.getStatusCheckedByUserLastTime().name(),
                    order.getStatusCheckedByAdminLastTime() == null ? null : order.getStatusCheckedByAdminLastTime().name(),
                    orderItemDtoList
            );
            return orderDto;
        }
        return null;
    }

    /**
     * Dto to Entity conversion
     * @param orderDto
     * @return order
     */
    public Order orderDtoToEntity(OrderDto orderDto) {
        if (orderDto != null) {
            // get user by userId
            User user = userRepository.findById(orderDto.getUserDto().getUserId()).orElse(null);

            // validate if user exists
            if (user != null) {
                try {
                    // catch exception
                    OrderStatus orderStatus = OrderStatus.valueOf(orderDto.getStatus().toUpperCase());
                    OrderStatus orderStatusCheckedByUser = OrderStatus.valueOf(orderDto.getStatusCheckedByUserLastTime().toUpperCase());

                    Order order = new Order(
                            orderDto.getOrderId(),
                            user,
                            orderDto.getOrderDate(),
                            orderDto.getUserFullName(),
                            orderDto.getAddress(),
                            orderStatus,
                            orderStatusCheckedByUser
                    );

                    // construct orderItem list in the order
                    List<OrderItem> orderItemList = new ArrayList<>();
                    for (int i = 0; i < orderDto.getOrderItemDtoList().size(); i++) {
                        OrderItemDto orderItemDto = orderDto.getOrderItemDtoList().get(i);
                        // convert orderItemDto to Entity
                        if (orderItemDto != null) {
                            // get product
                            Product product = productService.getProductById(orderItemDto.getProductDto().getProductId());
                            if (product != null) {
                                orderItemList.add(new OrderItem(
                                                orderItemDto.getOrderItemId(),
                                                order,
                                                product,
                                                orderItemDto.getQuantity()
                                        )
                                );
                            }
                        }
                    }
                    // set order items
                    order.setOrderItemList(orderItemList);
                    return order;
                }
                catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
