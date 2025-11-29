package com.url_shortener.urls.entity;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageResult<T> (
    List<T> data,
    int pageNumber,
    int totalPages,
    long totalElement,
    boolean isFirst,
    boolean isLast,
    boolean hasNext,
    boolean hasPrevious
) {
    public static <T> PageResult<T> from(Page<T> page) {
        return new PageResult<>(
            page.getContent(),
            page.getNumber() + 1,
            page.getTotalPages(),
            page.getTotalElements(),
            page.isFirst(),
            page.isLast(),
            page.hasNext(),
            page.hasPrevious()
        );
    }
	
}