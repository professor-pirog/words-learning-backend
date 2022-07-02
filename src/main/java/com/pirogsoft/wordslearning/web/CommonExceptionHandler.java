package com.pirogsoft.wordslearning.web;

import com.pirogsoft.wordslearning.dto.error.ErrorDTO;
import com.pirogsoft.wordslearning.dto.error.ErrorType;
import com.pirogsoft.wordslearning.exception.WordSetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception, WebRequest request) {
        log.error("Error has occurred", exception);
        return process(exception, mapErrorType(exception));
    }

    private ResponseEntity<ErrorDTO> process(Exception exception, ErrorType errorType) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .technicalDescription(exception.getMessage())
                .message(errorType.getDescription())
                .errorType(errorType).build();
        return ResponseEntity.status(errorType.getHttpStatus()).body(errorDTO);
    }

    private ErrorType mapErrorType(Exception exception) {
        if (exception instanceof WordSetNotFoundException) return ErrorType.WORD_SET_NOT_FOUND;
        if (exception instanceof MethodArgumentTypeMismatchException) return ErrorType.BAD_REQUEST;
        else return ErrorType.UNEXPECTED_ERROR;
    }
}
