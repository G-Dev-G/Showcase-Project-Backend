package com.project.backend.controller;

import com.project.backend.dto.OrderDto;
import com.project.backend.model.*;
import com.project.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getById/{id}")
    public OrderDto getOrderById(@PathVariable(value="id") Long id) {
        Order gotOrder = orderService.getOrderById(id);
        return orderService.orderEntityToDto(gotOrder);
    }

    @GetMapping("/getAllByUserId/{userId}")
    public List<OrderDto> getAllExistingOrdersByUserId(@PathVariable(value="userId") Long userId) {
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orderService.getAllExistingOrdersByUserId(userId)) {
            orderDtoList.add(orderService.orderEntityToDto(order));
        }
        return orderDtoList;
    }

    @GetMapping("/getAll")
    public List<OrderDto> getAllOrders() {
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orderService.getAllOrders()) {
            orderDtoList.add(orderService.orderEntityToDto(order));
        }
        return orderDtoList;
    }

    @PostMapping("/add")
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.orderDtoToEntity(orderDto);
        Order orderAdded = orderService.addOrder(order);
        return orderService.orderEntityToDto(orderAdded); // always return DTO
    }

    @PutMapping("/updateOrderStatusUser")
    public OrderDto updateOrderStatusUser(@RequestParam("orderId") Long orderId, @RequestParam("status") String status) {
        Order orderUpdated = orderService.updateOrderStatusUser(orderId, status);
        return orderService.orderEntityToDto(orderUpdated);
    }

    @PutMapping("/updateOrderStatusAdmin")
    public OrderDto updateOrderStatusAdmin(@RequestParam("orderId") Long orderId, @RequestParam("status") String status) {
        Order orderUpdated = orderService.updateOrderStatusAdmin(orderId, status);
        return orderService.orderEntityToDto(orderUpdated);
    }

    @DeleteMapping("/delete/{id}")
    public OrderDto deleteOrder(@PathVariable(value="id") Long id) {
        Order deletedOrder = orderService.deleteOrder(id);
        return orderService.orderEntityToDto(deletedOrder);
    }

}
