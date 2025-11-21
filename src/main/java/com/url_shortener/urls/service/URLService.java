package com.url_shortener.urls.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.url_shortener.urls.entity.InputDTO;
import com.url_shortener.urls.entity.OutputDTO;
import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.repository.URLRepository;

@Service
public class URLService {
    private final URLRepository urlRepository;
    private final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

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
        
        Optional<URLEntity> existing = urlRepository.findByOriginalURL(inputDTO.inputURL()); // find the existing url by inputDTO, not by code
        
        // Optional.isPresent: check if the value exist == Optional.isEmpty
        // Optional.get: access the data inside Optional, in this case access URLEntity; but if empty optional -> throw NoSuchElementException == orElseThrow == orElseThrow(exceptionSupplier)
        if (existing.isPresent()) {
            return new OutputDTO(
                existing.get().getID(),
                existing.get().getOriginalURL(),
                existing.get().getShortCode()
            );
        }
        
        URLEntity urlEntity = new URLEntity();
        urlEntity.setOriginalURL(inputDTO.inputURL());

        URLEntity savedEntity = urlRepository.save(urlEntity);

        String code = base62(savedEntity.getID());
        savedEntity.setShortCode(code);

        urlRepository.saveAndFlush(savedEntity);
        return new OutputDTO(
                savedEntity.getID(),
                savedEntity.getOriginalURL(),
                savedEntity.getShortCode()
        );
    }

    public String getOriginalURL(String shortCode) {
        // Optional.orElse: return a default value if not exist
        return urlRepository.findByShortCode(shortCode)
                            .map(URLEntity::getOriginalURL)
                            .orElse(null);
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