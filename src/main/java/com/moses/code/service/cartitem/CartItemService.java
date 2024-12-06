package com.moses.code.service.cartitem;

import com.moses.code.entity.Cart;
import com.moses.code.entity.CartItem;
import com.moses.code.entity.Product;
import com.moses.code.exception.ProductNotFoundException;
import com.moses.code.repository.CartItemRepository;
import com.moses.code.repository.CartRepository;
import com.moses.code.service.cart.ICartService;
import com.moses.code.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        boolean success = false;
        int retries = 3;

        while (!success && retries > 0) {
            try {
                Cart cart = cartService.getCart(cartId);
                Product product = productService.getProductById(productId);
                CartItem cartItem = cart.getItems()
                        .stream()
                        .filter(item -> item.getProduct().getId().equals(productId))
                        .findFirst()
                        .orElse(null);

                if (cartItem == null) {
                    cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setProduct(product);
                    cartItem.setQuantity(quantity);
                    cartItem.setUnitPrice(product.getPrice());
                    cart.addItem(cartItem);
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                }

                cartRepository.save(cart);
                success = true;
            } catch (OptimisticLockingFailureException ex) {
                retries--;
                if (retries == 0) throw ex;
            }
        }
    }



    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
//                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ProductNotFoundException("Item not found"));
    }

}