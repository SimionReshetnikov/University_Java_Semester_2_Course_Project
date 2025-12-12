package com.example.webshop.service;

import com.example.webshop.entity.Favorite;
import com.example.webshop.entity.Product;
import com.example.webshop.entity.User;
import com.example.webshop.repository.FavoriteRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void addToFavorites(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        Product product = productRepository.findById(productId)
                .orElseThrow();

        // не дублируем
        if (favoriteRepository.findByUserAndProductId(user, productId).isEmpty()) {
            Favorite fav = new Favorite();
            fav.setUser(user);
            fav.setProduct(product);
            favoriteRepository.save(fav);
        }
    }

    public void removeFromFavorites(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        favoriteRepository.findByUserAndProductId(user, productId)
                .ifPresent(favoriteRepository::delete);
    }

    public List<Favorite> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        return favoriteRepository.findByUser(user);
    }
}
