package com.url_shortener.urls.entity;

import jakarta.validation.constraints.NotBlank;

public record InputDTO(
    @NotBlank(message = "Original URL is required")
    String inputURL, Boolean isPrivate) {
}
