package com.url_shortener.urls.service;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.url_shortener.urls.entity.UserEntity;
import com.url_shortener.urls.repository.UserRepository;

@Service
public class SecurityUtil {
    
    private final UserRepository userRepository;
    
    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken) ) {
            String email = authentication.getName();
            return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("[Authentication] Can't find user by the email")
            );
        }
        return null;
    }
}