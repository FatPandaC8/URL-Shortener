package com.url_shortener.urls.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLController {
    @GetMapping("/")
    public String index() {
        return "This is a URL shortener";
    }
}
