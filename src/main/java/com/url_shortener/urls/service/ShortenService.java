package com.url_shortener.urls.service;

import com.url_shortener.urls.entity.InputDTO;
import com.url_shortener.urls.entity.OutputDTO;
import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.repository.URLRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ShortenService {

    private final URLRepository urlRepository;
    private final char[] ALPHABET =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public ShortenService(URLRepository urlRepository) {
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

        Optional<URLEntity> existing = urlRepository.findByOriginalURL(
            inputDTO.inputURL()
        ); // find the existing url by inputDTO, not by code

        // Optional.isPresent: check if the value exist == Optional.isEmpty
        // Optional.get: access the data inside Optional, in this case access URLEntity; but if empty optional -> throw NoSuchElementException == orElseThrow == orElseThrow(exceptionSupplier)
        if (existing.isPresent()) {
            return new OutputDTO(
                existing.get().getId(),
                existing.get().getOriginalURL(),
                existing.get().getShortCode()
            );
        }

        URLEntity urlEntity = new URLEntity();
        urlEntity.setOriginalURL(inputDTO.inputURL());
        urlEntity.setIsPrivate(inputDTO.isPrivate() != null ? inputDTO.isPrivate() : false);

        String code = getDeterministicShortCode(inputDTO.inputURL());
        if (code == null || code.isBlank()) {
            throw new IllegalStateException("Generated shortCode cannot be null or blank");
        }
        urlEntity.setShortCode(code);

        URLEntity savedEntity = urlRepository.save(urlEntity);
    
        return new OutputDTO(
            savedEntity.getId(),
            savedEntity.getOriginalURL(),
            savedEntity.getShortCode()
        );
    }

    private String getDeterministicShortCode(String originalURL) {
        // 1. Hash the URL
        long hash = Math.abs(originalURL.hashCode()); // simple hash; you can use MD5/SHA1 for longer codes
        if (hash < 0) hash = -(long)hash;
        // 2. Convert hash to base62
        return base62(hash);
    }

    private String base62(long id) {
        if (id == 0) {
            return "0";
        }
        char[] buffer = new char[11];
        int pos = 11;
        while (id != 0) {
            buffer[--pos] = ALPHABET[(int) (id % 62)];
            id /= 62;
        }
        return new String(buffer, pos, 11 - pos);
    }
}

/*
OPTIONAL (extended):
- ifPresentOrElse(consumer, runnable): do an action if value exist, else do another action
- ifPresent(consumer)                : do something if present
- filter(filterPredicate)            : given a predicate,           return according optional
- map(mappingFunction)               : take a function as a param and apply it to the value if present
*/
