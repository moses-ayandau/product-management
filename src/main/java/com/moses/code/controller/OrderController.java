package com.moses.code.controller;

import com.moses.code.dto.OrderDto;
import com.moses.code.entity.Order;
import com.moses.code.exception.NotFoundException;
import com.moses.code.mappers.OrderMapper;
import com.moses.code.service.order.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Operation(summary = "Place a new order", description = "Create an order for a specific user by providing the user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long userId) {
        Order order = orderService.placeOrder(userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @Operation(summary = "Get order details by ID", description = "Retrieve the details of a specific order by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with ID: " + orderId);
        }
        OrderDto orderDto = OrderMapper.convertFromOrderToOrderDto(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @Operation(summary = "Get all orders of a user", description = "Retrieve all orders placed by a specific user using their user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No orders found for the user")
    })
    @GetMapping("/{userId}/user")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderDto> orderDtos = orders.stream()
                .map(OrderMapper::convertFromOrderToOrderDto)
                .toList();
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
}
