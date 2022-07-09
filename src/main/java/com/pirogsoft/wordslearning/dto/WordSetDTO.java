package com.pirogsoft.wordslearning.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class WordSetDTO {

    private long id;

    private String name;

    private Instant createdAt;

    private Instant updatedAt;
}
