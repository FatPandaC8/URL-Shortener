package com.url_shortener.urls.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String originalURL;
    private String shortCode;

    public URLEntity(String originalURL, String shortCode) {
        this.originalURL = originalURL;
        this.shortCode = shortCode;
    }

    public Long getID() {
        return id;
    }

    public String getLongURL() {
        return originalURL;
    }

    public String getShortCode() {
        return shortCode;
    }
}
