package com.jpdrpenna.url_shortener.repository;

import com.jpdrpenna.url_shortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<Url, String> {
    Url findByShortUrlCode(String shortUrlCode);

}
