package com.example.atividade1.service;

import com.example.atividade1.model.Category;
import com.example.atividade1.model.NewsItem;
import com.example.atividade1.repository.NewsItemRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RssService {

    private final NewsItemRepository newsItemRepository;
    private final CategoryService categoryService;

    public RssService(NewsItemRepository newsItemRepository, CategoryService categoryService) {
        this.newsItemRepository = newsItemRepository;
        this.categoryService = categoryService;
    }

    private String extractImageUrl(String description) {
        if (description != null) {
            Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                return matcher.group(1);  // Retorna a primeira correspondência encontrada
            }
        }
        return null;  // Retorna null se não encontrar nenhuma imagem
    }

    @Transactional
    public void updateNewsFromCategory(Category category) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(category.getRssUrl())));
        List<SyndEntry> entries = feed.getEntries();
        for (SyndEntry entry : entries) {
            String title = entry.getTitle();
            String link = entry.getLink();

            Optional<NewsItem> existingNewsItem = newsItemRepository.findByTitleAndLink(title, link);
            if (existingNewsItem.isPresent()) {
                continue; // Skip if the news item already exists
            }

            NewsItem newsItem = new NewsItem();
            newsItem.setTitle(title);
            newsItem.setLink(link);
            newsItem.setPublicationDate(entry.getPublishedDate() != null ? entry.getPublishedDate() : new Date());
            newsItem.setImageUrl(extractImageUrl(entry.getDescription().getValue()));
            newsItem.setCategory(category);
            newsItemRepository.save(newsItem);
        }
    }
}
