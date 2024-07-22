package com.example.atividade1.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 2048)
    private String rssUrl;

    public Category() {}

    public Category(String name, String rssUrl) {
        this.name = name;
        this.rssUrl = rssUrl;
    }

    // Getters and setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}