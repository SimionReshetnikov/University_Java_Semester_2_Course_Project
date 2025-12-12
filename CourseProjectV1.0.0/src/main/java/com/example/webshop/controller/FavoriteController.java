package com.example.webshop.controller;

import com.example.webshop.entity.Favorite;
import com.example.webshop.service.FavoriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add/{productId}")
    public String addToFavorites(@PathVariable Long productId, Principal principal) {
        if (principal == null) return "redirect:/login";
        favoriteService.addToFavorites(principal.getName(), productId);
        return "redirect:/products";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromFavorites(@PathVariable Long productId, Principal principal) {
        if (principal == null) return "redirect:/login";
        favoriteService.removeFromFavorites(principal.getName(), productId);
        return "redirect:/favorites";
    }

    @GetMapping
    public String favorites(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        List<Favorite> favorites = favoriteService.getFavorites(principal.getName());
        model.addAttribute("favorites", favorites);
        return "favorites/list";
    }
}
