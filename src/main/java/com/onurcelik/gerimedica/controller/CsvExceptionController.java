package com.onurcelik.gerimedica.controller;

import com.onurcelik.gerimedica.exception.NoFoundException;
import com.onurcelik.gerimedica.exception.UnsupportedFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CsvExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Unexpected exception: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoFoundException.class)
    public ResponseEntity<Object> handleNoFoundException(NoFoundException ex) {
        log.error("No found: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedFileException.class)
    public ResponseEntity<Object> handleUnsupportedFileException(UnsupportedFileException ex) {
        log.error("Unsupported file exception: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
