package com.example.atividade1.scheduler;

import com.example.atividade1.service.RssService;
import com.example.atividade1.model.Category;
import com.example.atividade1.service.CategoryService;
import com.rometools.rome.io.FeedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RssScheduler {

    private final RssService rssService;
    private final CategoryService categoryService;

    public RssScheduler(RssService rssService, CategoryService categoryService) {
        this.rssService = rssService;
        this.categoryService = categoryService;
    }

    @Scheduled(fixedRate = 3600000) // 1 hora
    public void fetchNews() {
        try {
            List<Category> categories = categoryService.findAll();
            for (Category category : categories) {
                rssService.updateNewsFromCategory(category);
            }
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }
    }
}
