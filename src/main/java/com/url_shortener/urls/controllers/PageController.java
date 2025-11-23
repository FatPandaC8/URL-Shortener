package com.url_shortener.urls.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
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
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterHome() {
        return "register";
    }
    
}
