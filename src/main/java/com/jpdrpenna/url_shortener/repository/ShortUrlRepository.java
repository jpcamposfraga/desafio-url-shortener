package com.jpdrpenna.url_shortener.repository;

import com.jpdrpenna.url_shortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ShortUrlRepository extends JpaRepository<Url, String> {
    Url findByShortUrlCode(String shortUrlCode);
    int deleteByExpiresAtBefore(LocalDateTime now);

}
