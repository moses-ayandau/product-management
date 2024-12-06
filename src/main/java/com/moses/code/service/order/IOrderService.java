package com.moses.code.service.order;

import com.moses.code.entity.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderService {
    @Transactional
    Order placeOrder(Long userId);

    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
