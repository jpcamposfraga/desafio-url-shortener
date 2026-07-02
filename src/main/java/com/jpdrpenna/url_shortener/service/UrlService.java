package com.jpdrpenna.url_shortener.service;

import com.jpdrpenna.url_shortener.entity.Url;
import com.jpdrpenna.url_shortener.entity.dto.ShortenUrlRequest;
import com.jpdrpenna.url_shortener.entity.dto.UrlDTO;
import com.jpdrpenna.url_shortener.exceptions.ExpiredUrlException;
import com.jpdrpenna.url_shortener.exceptions.UrlNotFoundException;
import com.jpdrpenna.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final ShortUrlRepository repository;
    private final ShortCodeGeneratorService service;
    private final Random random = new Random();

    @Value("${app.base-url}")
    private String baseUrl;

    public String createAndReturnShortUrl(ShortenUrlRequest request){
        Url url = createUrl(request.url());
        UrlDTO dto =  new UrlDTO(url.getShortUrlCode());
        return generateLink(dto);
    }

    public Url findByShortUrl(String shortUrl){
        Url url = repository.findById(shortUrl).orElseThrow(()->new UrlNotFoundException("Short url not found"));
        if (isExpiredUrl(url)){
            throw new ExpiredUrlException("Url has expired");
        }
        return url;
    }

    private Url createUrl(String originalUrl){
        if (!originalUrl.startsWith("https") && !originalUrl.startsWith("http")){
            originalUrl = "https://" + originalUrl;
        }

        Url url = new Url(originalUrl, service.generateShortCode(random.nextInt(5, 11)), LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
        return repository.save(url);
    }

    private String generateLink(UrlDTO urlDTO){
        if (urlDTO!=null){
            return baseUrl+ "/api/"+ urlDTO.shortUrlCode();
        }
        return null;
    }

    private boolean isExpiredUrl(Url url){
        return LocalDateTime.now().isAfter(url.getExpiresAt());
    }


}
