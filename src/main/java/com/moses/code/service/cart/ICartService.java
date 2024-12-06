package com.moses.code.service.cart;

import com.moses.code.entity.Cart;
import com.moses.code.entity.CartItem;
import com.moses.code.exception.UserNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {
    public Cart getCart(Long id);



    public void clearCart(Long id);

    public BigDecimal getTotalPrice(Long id);

    public Long initializeNewCart();

    public Cart getCartByUserId(Long userId);
}
