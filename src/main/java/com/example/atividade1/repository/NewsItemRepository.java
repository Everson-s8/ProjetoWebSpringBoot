package com.example.atividade1.repository;

import com.example.atividade1.model.Category;
import com.example.atividade1.model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    List<NewsItem> findAllByCategory(Category category);
    List<NewsItem> findAllByCategoryIn(Set<Category> categories);

    @Query("SELECT n FROM NewsItem n ORDER BY n.publicationDate DESC")
    List<NewsItem> findAllOrderByPublicationDateDesc();

    Optional<NewsItem> findByTitleAndLink(String title, String link);
}
