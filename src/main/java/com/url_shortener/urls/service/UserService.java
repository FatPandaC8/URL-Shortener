package com.url_shortener.urls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.url_shortener.urls.entity.UserEntity;
import com.url_shortener.urls.repository.UserRepository;

/**
 * Autowired : resolve and inject collaborating beans into out bean
 * Autowiring on properties, setters and constructors
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerUser(String username, String email, String rawPassword) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(rawPassword));
        userEntity.setRole_num(1);
        return userRepository.save(userEntity);
    }

    public UserEntity registerAdmin(String username, String email, String rawPassword) {
        UserEntity admin = new UserEntity();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setRole_num(2); // 2 = admin
        return userRepository.save(admin);
    }
    
    /*
    ADMIN: 
    bach123@gmail.com
    123
    
    USER:
    user123@gmail.com
    123
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
        .orElseGet(() -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + username))
        );
                        
        return org.springframework.security.core.userdetails.User.builder()
        .username(userEntity.getEmail())
        .password(userEntity.getPassword())
        .roles(userEntity.getRole_num() == 2 ? "ADMIN" : "USER")
        .build();
    }
}
