package com.moses.code.controller;

import com.moses.code.dto.CartDto;
import com.moses.code.entity.Cart;
import com.moses.code.mappers.CartMapper;
import com.moses.code.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {

            Cart cart = cartService.getCart(cartId);
            CartDto cartDto = CartMapper.convertFromCartToCartDto(cart);
            return ResponseEntity.ok(cartDto);

    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> clearCart( @PathVariable Long cartId) {

            cartService.clearCart(cartId);
            return new ResponseEntity<>("Clear Cart Success!", NOT_FOUND);

    }

    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<BigDecimal> getTotalAmount( @PathVariable Long cartId) {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);

    }
}
