package com.jpdrpenna.url_shortener;

import com.jpdrpenna.url_shortener.entity.Url;
import com.jpdrpenna.url_shortener.entity.dto.ShortenUrlRequest;
import com.jpdrpenna.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/shorten-url")
    public ResponseEntity<String> createShortUrl(@RequestBody String originalUrl){
        ShortenUrlRequest request = new ShortenUrlRequest(originalUrl);
        String response = urlService.createAndReturnShortUrl(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl){
        Url foundUrl = urlService.findByShortUrl(shortUrl);
        String originalLink = foundUrl.getOriginalUrl();

        return ResponseEntity.ok(originalLink);
    }
}
