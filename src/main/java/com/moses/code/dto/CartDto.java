package com.moses.code.dto;

import com.moses.code.entity.CartItem;
import com.moses.code.entity.User;
import jdk.jshell.Snippet;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {


    private Long id;
    private BigDecimal totalAmount;


    private Set<CartItem> items;


    private User user;

}
