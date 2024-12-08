package com.moses.code.mappers;

import com.moses.code.dto.CartDto;
import com.moses.code.entity.Cart;

public class CartMapper {
    public static Cart convertFromCartDtoToCart(CartDto cartDto){
        Cart cart = new Cart();
        cart.setUser(cartDto.getUser());
        cart.setTotalAmount(cartDto.getTotalAmount());
        cart.setItems(cartDto.getItems());
        return cart;
    }

    public static CartDto convertFromCartToCartDto(Cart cart){
        CartDto cartDto =new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setItems(cart.getItems());
        cartDto.setUser(cart.getUser());
        cartDto.setTotalAmount(cart.getTotalAmount());
        return cartDto;
    }
}