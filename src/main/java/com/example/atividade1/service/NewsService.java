package com.example.atividade1.service;

import com.example.atividade1.model.Category;
import com.example.atividade1.model.NewsItem;
import com.example.atividade1.repository.CategoryRepository;
import com.example.atividade1.repository.NewsItemRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private final NewsItemRepository newsItemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public NewsService(NewsItemRepository newsItemRepository, CategoryRepository categoryRepository) {
        this.newsItemRepository = newsItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void updateNewsForAllCategories() throws IOException, FeedException {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            updateNewsFromCategory(category);
        }
    }

    private void updateNewsFromCategory(Category category) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(category.getRssUrl())));
        List<SyndEntry> entries = feed.getEntries();
        for (SyndEntry entry : entries) {
            NewsItem newsItem = new NewsItem();
            newsItem.setTitle(entry.getTitle());
            newsItem.setLink(entry.getLink());
            newsItem.setPublicationDate(entry.getPublishedDate() != null ? entry.getPublishedDate() : new Date());
            newsItem.setImageUrl(entry.getDescription().getValue()); // Simplified, needs parsing
            newsItem.setCategory(category);
            newsItemRepository.save(newsItem);
        }
    }

    public List<NewsItem> getNewsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return newsItemRepository.findAllByCategory(category);
    }

    public List<NewsItem> getAllNews() {
        return newsItemRepository.findAll();
    }
}
