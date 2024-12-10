package com.moses.code.controller;

import com.moses.code.service.cart.ICartService;
import com.moses.code.service.cartitem.ICartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartitem")
public class CartItemController {

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartService cartService;

    @Operation(summary = "Add item to cart", description = "Add an item to the cart by specifying cart ID, product ID, quantity, and user ID. If the cart ID is not provided, a new cart will be initialized.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added to cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Product or User not found")
    })
    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam Long userId) {
        if (cartId == null) {
            cartId = cartService.initializeNewCart();
        }
        cartItemService.addItemToCart(cartId, productId, quantity, userId);
        return new ResponseEntity<>("Item added to cart", HttpStatus.OK);
    }

    @Operation(summary = "Remove item from cart", description = "Remove a specific item from a cart by providing the cart ID and item ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed from cart successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or Item not found")
    })
    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return new ResponseEntity<>("Item removed from cart", HttpStatus.OK);
    }

    @Operation(summary = "Update cart item quantity", description = "Update the quantity of a specific item in the cart by specifying the cart ID, item ID, and the new quantity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quantity value"),
            @ApiResponse(responseCode = "404", description = "Cart or Item not found")
    })
    @PutMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return new ResponseEntity<>("Cart item updated", HttpStatus.OK);
    }
}
