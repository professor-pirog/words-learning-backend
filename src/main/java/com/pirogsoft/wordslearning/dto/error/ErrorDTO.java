package com.pirogsoft.wordslearning.dto.error;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Data
@Builder
public class ErrorDTO {

    private final String message;

    private final String technicalDescription;

    private final ErrorType errorType;

    private final Instant timestamp = Instant.now();
}
