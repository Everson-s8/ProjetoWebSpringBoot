package com.example.atividade1.controller;

import com.example.atividade1.model.NewsItem;
import com.example.atividade1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.atividade1.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsItemRepository newsItemRepository;

    @GetMapping("/recent")
    public ResponseEntity<List<NewsItem>> getRecentNews() {
        List<NewsItem> newsItems = newsItemRepository.findAllOrderByPublicationDateDesc();
        return ResponseEntity.ok(newsItems);
    }

    @Autowired
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/category/{categoryId}")
    public List<NewsItem> getNewsByCategory(@PathVariable Long categoryId) {
        return newsService.getNewsByCategoryId(categoryId);
    }

    @GetMapping("/all")
    public List<NewsItem> getAllNews() {
        return newsService.getAllNews();
    }
}
