package com.url_shortener.urls.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class URLService {
    private final Map<String, String> storage = new HashMap<>();

    public String shortenURL(String longURL) {
        String code = generateCode();
        storage.put(code, longURL);
        return code;
    }

    public String getLongURL(String code) {
        return storage.get(code);
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
