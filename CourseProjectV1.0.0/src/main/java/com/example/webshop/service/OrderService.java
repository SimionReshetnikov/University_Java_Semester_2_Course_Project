package com.example.webshop.service;

import com.example.webshop.entity.*;
import com.example.webshop.repository.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        UserRepository userRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    public Order createOrder(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getUserCart(username);

        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);

        // переносим товары из корзины в заказ
        Order finalOrder = order;
        cart.getItems().forEach(cartItem -> {
            OrderItem item = new OrderItem();
            item.setOrder(finalOrder);
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(item);
        });

        // очищаем корзину
        cartService.clearCart(username);

        return order;
    }

    public Order findByIdForUser(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Проверяем, что заказ принадлежит пользователю
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied to order " + id);
        }

        return order;
    }
}
