package com.example.atividade1.controller;

import com.example.atividade1.model.Category;
import com.example.atividade1.model.NewsItem;
import com.example.atividade1.model.User;
import com.example.atividade1.repository.CategoryRepository;
import com.example.atividade1.repository.NewsItemRepository;
import com.example.atividade1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsItemRepository newsItemRepository;

    @PostMapping("/favorites")
    public ResponseEntity<?> setFavoriteCategories(@RequestBody Set<Long> categoryIds) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(currentUsername);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Set<Category> favoriteCategories = new HashSet<>();
        for (Long categoryId : categoryIds) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            categoryOptional.ifPresent(favoriteCategories::add);
        }

        user.setFavoriteCategories(favoriteCategories);
        userRepository.save(user);
        return ResponseEntity.ok("Favorite categories updated successfully");
    }

    @GetMapping("/favorites")
    public ResponseEntity<?> getFavoriteCategories() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(currentUsername);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Set<Category> favoriteCategories = user.getFavoriteCategories();
        return ResponseEntity.ok(favoriteCategories);
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNewsByFavorites() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(currentUsername);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Set<Category> favoriteCategories = user.getFavoriteCategories();
        List<NewsItem> newsItems;

        if (favoriteCategories.isEmpty()) {
            newsItems = newsItemRepository.findAll();
        } else {
            newsItems = newsItemRepository.findAllByCategoryIn(favoriteCategories);
        }

        return ResponseEntity.ok(newsItems);
    }
}
