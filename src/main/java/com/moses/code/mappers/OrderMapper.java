package com.moses.code.mappers;

import com.moses.code.dto.OrderDto;
import com.moses.code.entity.Order;

public class OrderMapper {
    public static OrderDto convertFromOrderToOrderDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderItems(order.getOrderItems());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setId(order.getId());
        orderDto.setUser(order.getUser());
        orderDto.setTotalAmount(order.getTotalAmount());
        return  orderDto;
    }

}
