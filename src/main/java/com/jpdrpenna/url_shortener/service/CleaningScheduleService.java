package com.jpdrpenna.url_shortener.service;

import com.jpdrpenna.url_shortener.repository.ShortUrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CleaningScheduleService {

    private final ShortUrlRepository repository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void scheduledCleaning(){
        int deleted = repository.deleteByExpiresAtBefore(LocalDateTime.now());
        if(deleted > 0){
            System.out.println("[CleaningScheduleService] Deleted "+deleted+" records");
        }

    }
}
