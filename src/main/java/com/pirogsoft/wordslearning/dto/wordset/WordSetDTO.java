package com.pirogsoft.wordslearning.dto.wordset;

import com.pirogsoft.wordslearning.model.Language;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class WordSetDTO {

    private Long id;

    private String name;

    private Language language;

    private Instant createdAt;

    private Instant updatedAt;

    private int wordCount;
}
