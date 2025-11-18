package com.url_shortener.urls.service;

import java.util.UUID;

import com.url_shortener.urls.entity.InputDTO;
import com.url_shortener.urls.entity.OutputDTO;
import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.repository.URLRepository;
import org.springframework.stereotype.Service;

@Service
public class URLService {
    private final URLRepository urlRepository;

    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * Industry standard:
     * - Store the long URL in DB -> DB generate an integer ID
     * - Convert that to base62
     * - That base62 is your short code
     * => Hard for distributed systems -> hard for synchronous
     * @param inputDTO long url
     * @return shortened url
     */
    public OutputDTO shortenURL(InputDTO inputDTO) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("Input DTO is null");
        }

        String code = generateCode();

        URLEntity urlEntity = new URLEntity(inputDTO.inputURL(), code);

        URLEntity savedEntity = urlRepository.save(urlEntity);

        return new OutputDTO(
                savedEntity.getID(),
                savedEntity.getLongURL(),
                savedEntity.getShortCode()
        );
    }

    public String getLongURL(String code) {
        URLEntity urlEntity = urlRepository.findByShortCode(code);

        return urlEntity.getLongURL();
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
