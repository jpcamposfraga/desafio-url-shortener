package com.jpdrpenna.url_shortener.service;

import com.jpdrpenna.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
@RequiredArgsConstructor
public class ShortCodeGeneratorService {
    private final ShortUrlRepository repository;
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateShortCode(int length){

        StringBuilder strBuilder = new StringBuilder(CHARS.length());
        for (int i = 0; i<length; i++){
            int index = RANDOM.nextInt(CHARS.length());
            strBuilder.append(CHARS.charAt(index));
        }
        if (repository.existsById(strBuilder.toString())){
            return generateShortCode(length);
        }
        return strBuilder.toString();
    }
}
