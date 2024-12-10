package com.moses.code.controller;

import com.moses.code.dto.CartDto;
import com.moses.code.entity.Cart;
import com.moses.code.mappers.CartMapper;
import com.moses.code.service.cart.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;



@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Operation(summary = "Get Cart Details", description = "Retrieve the details of a specific cart by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        CartDto cartDto = CartMapper.convertFromCartToCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    @Operation(summary = "Clear Cart", description = "Remove all items from a specific cart by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return new ResponseEntity<>("Clear Cart Success!", HttpStatus.OK);
    }

    @Operation(summary = "Get Cart Total Price", description = "Retrieve the total price of all items in a specific cart by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total price retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Long cartId) {
        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }
}
