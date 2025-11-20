package com.url_shortener.urls.controllers;

import com.url_shortener.urls.entity.InputDTO;
import com.url_shortener.urls.entity.OutputDTO;
import com.url_shortener.urls.service.URLService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class URLController {
    private final URLService urlService;

    public URLController(URLService urlService) {
        this.urlService = urlService;
        System.out.println("URLController created!");
    }

    /**
     * GetMapping: when someone sends requests to this path, run this method
     *             and return the result as the HTTP response.
     *
     * @param model  without it, just return the template name -> static rendering.
     *               with it, return template name + dynamic data the page can use
     *
     * @return home template name
     */
    @GetMapping({"/", "/home"})
    public String showHome(Model model) {
        return "index";
    }

    @PostMapping("/shorten")
    public String shorten(@RequestParam String originalURL, Model model) {
        OutputDTO outputDTO = urlService.shortenURL(new InputDTO(originalURL));

        model.addAttribute(
                "shortURL",
                "http://localhost:9090/"
                + outputDTO.shortCode());
        return "index";
    }

    /**
     * Spring boot "redirect:" is a keyword to sends a 302 response.
     * @param code shortCode
     * @return redirection to the original URL
     */
    @GetMapping("/{code}")
    public String redirect(@PathVariable String code) {
        String originalURL = urlService.getOriginalURL(code);
        if (originalURL == null) {
            return "error";
        }

        return "redirect:" + originalURL;
    }
}
