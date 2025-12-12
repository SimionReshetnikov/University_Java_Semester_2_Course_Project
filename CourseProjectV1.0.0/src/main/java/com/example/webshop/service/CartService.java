package com.example.webshop.service;

import com.example.webshop.entity.Cart;
import com.example.webshop.entity.CartItem;
import com.example.webshop.entity.Product;
import com.example.webshop.entity.User;
import com.example.webshop.repository.CartRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getUserCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public void addToCart(String username, Long productId) {
        Cart cart = getUserCart(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден: id=" + productId));

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem == null) {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(1);
            cart.getItems().add(item);
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(String username, Long cartItemId) {
        Cart cart = getUserCart(username);
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(String username) {
        Cart cart = getUserCart(username);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public void increaseItem(Long itemId, String username) {
        Cart cart = getUserCart(username);
        cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(item.getQuantity() + 1);
                    cartRepository.save(cart);
                });
    }

    public void decreaseItem(Long itemId, String username) {
        Cart cart = getUserCart(username);
        cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    int q = item.getQuantity() - 1;
                    if (q <= 0) {
                        cart.getItems().remove(item);
                    } else {
                        item.setQuantity(q);
                    }
                    cartRepository.save(cart);
                });
    }
}
