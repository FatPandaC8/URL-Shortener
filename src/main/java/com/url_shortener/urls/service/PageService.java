package com.url_shortener.urls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.url_shortener.urls.entity.PageResult;
import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.repository.URLRepository;

@Service
public class PageService {
    @Autowired
    private URLRepository urlRepository;
	
    public PageResult<URLEntity> findAllPublicShortURLs(int page) {
        page = page > 1 ? page - 1 : 0;
        Pageable pageable = PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        return PageResult.from(urlRepository.findPublicURLs(pageable));
    }
}