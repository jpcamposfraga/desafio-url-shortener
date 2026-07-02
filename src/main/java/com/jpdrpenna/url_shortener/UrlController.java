package com.jpdrpenna.url_shortener;

import com.jpdrpenna.url_shortener.entity.Url;
import com.jpdrpenna.url_shortener.entity.dto.ResponseUrlDTO;
import com.jpdrpenna.url_shortener.entity.dto.ShortenUrlRequest;
import com.jpdrpenna.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/shorten-url")
    public ResponseEntity<ResponseUrlDTO> createShortUrl(@RequestBody ShortenUrlRequest request){
        String shortUrlCode = urlService.createAndReturnShortUrl(request);
        ResponseUrlDTO response =  new ResponseUrlDTO(shortUrlCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrlCode}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrlCode){
        Url foundUrl = urlService.findByShortUrl(shortUrlCode);
        String originalLink = foundUrl.getOriginalUrl();

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalLink).build();
    }
}
