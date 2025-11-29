package com.url_shortener.urls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.url_shortener.urls.entity.URLEntity;
import com.url_shortener.urls.entity.UserEntity;

/**
 * Optional: handling value that can be null (aka throw NullPointerException)
 * API     : 
 * - Optional<String> emptyOptional = Optional.empty();                 // make an empty Optional object
 * - Optional<String> optionalWithNonNullValue = Optional.of("Hello");  // make an optional object that can not be null, if given null -> throw NullPointerException
 * - Optinal.ofNullable(value);                                         // make an optional object, if null -> empty optional, else make an optional of the value
 */
public interface URLRepository extends JpaRepository<URLEntity, Long> {
    Optional<URLEntity> findByOriginalURL(String originalURL);
    Optional<URLEntity> findByShortCode(String shortCode);
    List<URLEntity> findByCreatedBy(UserEntity currentUser);
    
    // It must select from the URLEntity not the table urls 
    @Query("select u from URLEntity u left join fetch u.createdBy where u.isPrivate = false")
    Page<URLEntity> findPublicURLs(Pageable pageable);
}
