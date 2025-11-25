package com.url_shortener.urls.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.repository.URLRepository;

@Controller
public class PageController {
    @Autowired
    private URLRepository urlRepository;
    /**
     * GetMapping: when someone sends requests to this path, run this method
     *             and return the result as the HTTP response.
     *
     * @param model  without it, just return the template name -> static rendering.
     *               with it, return template name + dynamic data the page can use
     *
     * @return home template name
     */
    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("url", new URLEntity()); // provide an empty URLEntity for the form -> needed for th:object in the form
        model.addAttribute("urls", urlRepository.findByIsPrivateIsFalseOrderByCreatedAtDesc());
        model.addAttribute("baseURL", "http://localhost:9090");
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterHome() {
        return "register";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
}
