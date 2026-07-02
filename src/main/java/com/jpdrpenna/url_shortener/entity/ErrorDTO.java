package com.jpdrpenna.url_shortener.entity;

import java.time.LocalDateTime;

public record ErrorDTO (int statusCode, String message, LocalDateTime timeStamp){
}
