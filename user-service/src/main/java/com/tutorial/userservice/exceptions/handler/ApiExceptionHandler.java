package com.tutorial.userservice.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tutorial.userservice.exceptions.dto.ApiExceptionResponseDTO;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponseDTO> handleException(Exception e) {
        ApiExceptionResponseDTO response = ApiExceptionResponseDTO.builder()
                .type("TECNICO")
                .title("Input Output Error")
                .code("1024")
                .detail("Error de conexión con el servidor")
                .instance("/errors/technical/io-error/1")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
