package com.adaneinstein.pedidos.controller;

import com.adaneinstein.pedidos.constants.ResponseMessages;
import com.adaneinstein.pedidos.dto.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleErrorRoot(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), ResponseMessages.ERROR.toString()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response<String>> handleError405(final HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new Response<>(e.getMessage(), ResponseMessages.ERROR.toString()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Response<String>> handleError400(final HttpClientErrorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(e.getMessage(), ResponseMessages.ERROR.toString()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response<String>> handleError404(final MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), ResponseMessages.ERROR.toString()));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Response<String>> handlePreconditionFailed(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>("Violação de interidade", ResponseMessages.ERROR.toString()));
    }

}
