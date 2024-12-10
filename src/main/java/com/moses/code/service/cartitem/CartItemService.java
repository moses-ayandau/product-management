package com.moses.code.service.cartitem;

import com.moses.code.entity.Cart;
import com.moses.code.entity.CartItem;
import com.moses.code.entity.Product;
import com.moses.code.entity.User;
import com.moses.code.exception.NotFoundException;
import com.moses.code.repository.CartItemRepository;
import com.moses.code.repository.CartRepository;
import com.moses.code.repository.UserRepository;
import com.moses.code.service.cart.ICartService;
import com.moses.code.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartItemService  implements ICartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity, Long userId) {
        boolean success = false;
        int retries = 3;

        while (!success && retries > 0) {
            try {
                Cart cart = cartService.getCart(cartId); // Get the cart by cartId
                Product product = productService.getProductById(productId); // Get the product by productId
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("User not found"));

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

                if (cart.getUser() == null) {
                    cart.setUser(user);
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
                .findFirst().orElseThrow(() -> new NotFoundException("Item not found"));
    }

}