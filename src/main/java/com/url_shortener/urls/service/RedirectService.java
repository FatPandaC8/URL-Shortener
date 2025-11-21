package com.url_shortener.urls.service;

import org.springframework.stereotype.Service;

import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.repository.URLRepository;

@Service
public class RedirectService {
    private final URLRepository uRLRepository;

    public RedirectService(URLRepository urlRepository) {
        this.uRLRepository = urlRepository;
    }

    public String getOriginalURL(String shortCode) {
        return uRLRepository.findByShortCode(shortCode)
                            .map(URLEntity::getOriginalURL)
                            .orElse(null);
    }
}
