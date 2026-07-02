package com.jpdrpenna.url_shortener.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "url")
public class Url {
    @Column(name = "original_url")
    String originalUrl;

    @Id
    @Column(name = "short-url_code")
    String ShortUrlCode;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "expires_at")
    LocalDateTime expiresAt;

}
