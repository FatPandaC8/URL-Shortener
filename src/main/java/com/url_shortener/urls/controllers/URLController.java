package com.url_shortener.urls.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url_shortener.urls.entity.InputDTO;
import com.url_shortener.urls.entity.OutputDTO;
import com.url_shortener.urls.service.RedirectService;
import com.url_shortener.urls.service.ShortenService;

@Controller
public class URLController {
    private final ShortenService shortenService;
    private final RedirectService redirectService;

    public URLController(ShortenService shortenService, RedirectService redirectService) {
        this.shortenService = shortenService;
        this.redirectService = redirectService;
    }

    @PostMapping("/shorten")
    public String shorten(@RequestParam String originalURL, Model model) {
        OutputDTO outputDTO = shortenService.shortenURL(new InputDTO(originalURL));

        model.addAttribute(
                "shortURL",
                "http://localhost:9090/"
                + outputDTO.shortCode());
        return "index";
    }

    /**
     * Spring boot "redirect:" is a keyword to sends a 302 response.
     * Now using ResponseEntity: HttpStatus.FOUND == 302 response (use 302 - not 301 (moved permanently) as we can later do click analytics)
     * @param code shortCode
     * @return redirection to the original URL
     */
    @SuppressWarnings("null") // for URI.create(originalURL)
    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalURL = redirectService.getOriginalURL(code);
        if (originalURL == null) return ResponseEntity.notFound().build();

        // URI.create(url) -> create an object (not hosting anything):
        // URI uri = URI.create("https://example.com/abc123");
        // System.out.println(uri.getHost()); // example.com
        // System.out.println(uri.getPath()); // /abc123
        
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create(originalURL))
                             .build();
    }
}

/*
Response Entity: represent whole HTTP response: status code, headers and body
- Must return it from the endpoint
- Can add custom header:
        @GetMapping("/customHeader")
        ResponseEntity<String> customHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "foo");
                
            return new ResponseEntity<>(
            "Custom header set", headers, HttpStatus.OK);
        }

- URI, URL, URN:
URI (Uniform Resource Identfier): a string of chars used to identity a resource on the internet either by location or by name or both

URL (Uniform Resource Locator): a string of chars but refers to just the address -> locate resource on the web
    + Protocol
    + Domain
    Example: http://www.example.com/something.php
    Protocol: http
    Domain: www.example.com/
    (May have a port)
    (Path: something.php)
    (Query String: ?fn=Flick&ls=lkcmd) 
    (Fragment: #External_link)

URN (Uniform Resource Name): name of resource

Since two or more resources can have the same name -> use use URLs to identify resources
*/