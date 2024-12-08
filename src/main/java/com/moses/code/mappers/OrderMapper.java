package com.moses.code.mappers;

import com.moses.code.dto.OrderDto;
import com.moses.code.dto.OrderItemDto;
import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Order;
import com.moses.code.entity.OrderItem;
import com.moses.code.entity.Product;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDto convertFromOrderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .userName(order.getUser().getFirstName())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderMapper::convertFromOrderItemToOrderItemDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private static OrderItemDto convertFromOrderItemToOrderItemDto(OrderItem item) {
        return OrderItemDto.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .product(convertFromProductToProductDto(item.getProduct()))
                .build();
    }

    private static ProductDto convertFromProductToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
