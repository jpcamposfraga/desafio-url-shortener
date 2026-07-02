package com.jpdrpenna.url_shortener.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class ShortCodeGeneratorService {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateShortCode(int length){

        StringBuilder strBuilder = new StringBuilder(CHARS.length());
        for (int i = 0; i<length; i++){
            int index = RANDOM.nextInt(CHARS.length());
            strBuilder.append(CHARS.charAt(index));
        }
        return strBuilder.toString();
    }
}
