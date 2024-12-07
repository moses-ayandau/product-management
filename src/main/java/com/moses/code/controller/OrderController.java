package com.moses.code.controller;

import com.moses.code.dto.OrderDto;
import com.moses.code.entity.Order;
import com.moses.code.exception.NotFoundException;
import com.moses.code.mappers.OrderMapper;
import com.moses.code.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long userId) {
            Order order =  orderService.placeOrder(userId);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
            Order order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @GetMapping("/{userId}/user")
public ResponseEntity<List<OrderDto>> clearOrder(@PathVariable Long userId){
        List<Order> orders =orderService.getUserOrders(userId);
        List<OrderDto> orderDtos = orders.stream()
                .map(OrderMapper::convertFromOrderToOrderDto)
                .toList();
        return new ResponseEntity<>(orderDtos, HttpStatus.NOT_FOUND);
    }
}
