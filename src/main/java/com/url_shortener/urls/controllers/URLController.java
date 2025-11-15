package com.url_shortener.urls.controllers;

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
     *             and return the result as the HTTP response
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
    public String shorten(@RequestParam String longURL, Model model) {
        String code = urlService.shortenURL(longURL);
        model.addAttribute("shortURL", "http://localhost:9090/" + code);
        return "index";
    }

    @GetMapping("/{code}")
    public String redirect(@PathVariable String code) {
        String longURL = urlService.getLongURL(code);
        if (longURL == null) {
            return "error";
        }

        return "redirect:" + longURL;
    }
}
