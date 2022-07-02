package com.pirogsoft.wordslearning.dto.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    WORD_SET_NOT_FOUND("Word set not found", HttpStatus.NOT_FOUND),
    UNEXPECTED_ERROR("Unexpected error occurs", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST);

    private final String description;

    private final HttpStatus httpStatus;

    ErrorType(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
