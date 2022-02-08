package com.meli.mutantdetector.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class MutantException extends RuntimeException{

    private final String errorMessage;
    private final HttpStatus statusCode;
}
