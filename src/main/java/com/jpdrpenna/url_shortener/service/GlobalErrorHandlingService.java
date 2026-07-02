package com.jpdrpenna.url_shortener.service;

import com.jpdrpenna.url_shortener.entity.ErrorDTO;
import com.jpdrpenna.url_shortener.exceptions.ExpiredUrlException;
import com.jpdrpenna.url_shortener.exceptions.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalErrorHandlingService extends ResponseEntityExceptionHandler {

    //global handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex){
        ErrorDTO message = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(ExpiredUrlException.class)
    public ResponseEntity<ErrorDTO> handleException(ExpiredUrlException ex){
        ErrorDTO message = new ErrorDTO(HttpStatus.FORBIDDEN.value(), "Url is expired!", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(UrlNotFoundException ex){
        ErrorDTO message = new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
