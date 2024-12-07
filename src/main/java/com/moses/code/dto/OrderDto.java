package com.moses.code.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moses.code.entity.OrderItem;
import com.moses.code.entity.OrderStatus;
import com.moses.code.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {

    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItem> orderItems;


    private User user;
}
