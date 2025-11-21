package com.url_shortener.urls.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
/**
 * Serialization   : conversion from a java object to byte stream -> save to a database or transfer over a network
 * Deserialization : do the opposite
 */
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable=false)
    private String originalURL;

    @Column(unique=true)
    private String shortCode;

    public URLEntity(String originalURL) {
        this.originalURL = originalURL;
    }

    public Long getID() {
        return id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public String getShortCode() {
        return shortCode;
    }
}
