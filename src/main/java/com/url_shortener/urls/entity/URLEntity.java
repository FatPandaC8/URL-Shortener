package com.url_shortener.urls.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="urls")
/**
 * Serialization   : conversion from a java object to byte stream -> save to a database or transfer over a network
 * Deserialization : do the opposite
 */
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String originalURL;

    @Column(unique=true, nullable = false)
    private String shortCode;

    public URLEntity(String originalURL) {
        this.originalURL = originalURL;
    }
    
    @ManyToOne(fetch = FetchType.LAZY) //each url belongs to one user, but one user can have many urls
    @JoinColumn(name = "createdBy")
    private UserEntity createdBy;
    
    private Boolean isPrivate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expireAt;
    
    @ColumnDefault("0")
    private Long clickCount;
}
