package com.url_shortener.urls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url_shortener.urls.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserID(Long userID);
}
