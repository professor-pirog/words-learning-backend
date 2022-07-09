package com.pirogsoft.wordslearning.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class WordSetDetailDTO {

    private long id;

    private String name;

    private Set<WordDTO> words;

    private Instant createdAt;

    private Instant updatedAt;
}
