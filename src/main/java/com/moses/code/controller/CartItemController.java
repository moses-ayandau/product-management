package com.moses.code.controller;

import com.moses.code.service.cart.ICartService;
import com.moses.code.service.cartitem.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cartitem")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;


    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity,
                                                    @RequestParam Long userId) {
            if (cartId == null) {
                cartId= cartService.initializeNewCart();
            }
            cartItemService.addItemToCart (cartId, productId, quantity, userId);
            return new ResponseEntity<>("Item added to cart" , HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
            cartItemService.removeItemFromCart(cartId, itemId);
            return new ResponseEntity<>("Item removed from cart", NOT_FOUND);

    }

    @PutMapping("/{cartId}/item/{itemId}")
    public  ResponseEntity<String> updateItemQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long itemId,
                                                           @RequestParam Integer quantity) {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return new ResponseEntity<>("Cart item updated", HttpStatus.OK);

    }
}
