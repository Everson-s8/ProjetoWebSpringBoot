package com.example.atividade1.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news_items")
public class NewsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition="TEXT")
    private String title;

    @Column(columnDefinition="TEXT")
    private String imageUrl;

    @Column(nullable = false, columnDefinition="TEXT")
    private String link;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // Garantir que a data seja armazenada corretamente
    private Date publicationDate;

    @ManyToOne(fetch = FetchType.EAGER) // Changed to EAGER loading
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters and setters...


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
