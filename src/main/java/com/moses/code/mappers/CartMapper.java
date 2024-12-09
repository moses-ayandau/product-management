package com.moses.code.mappers;

import com.moses.code.dto.CartDto;
import com.moses.code.entity.Cart;

public class CartMapper {

    public static Cart convertFromCartDtoToCart(CartDto cartDto) {
        return Cart.builder()
                .user(cartDto.getUser())
                .totalAmount(cartDto.getTotalAmount())
                .items(cartDto.getItems())
                .build();
    }

    public static CartDto convertFromCartToCartDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .user(cart.getUser())
                .totalAmount(cart.getTotalAmount())
                .items(cart.getItems())
                .build();
    }
}
