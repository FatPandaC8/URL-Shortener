package com.url_shortener.urls.repository;

import com.url_shortener.urls.entity.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URLEntity, Long> {
    URLEntity findByShortCode(String code);
}
