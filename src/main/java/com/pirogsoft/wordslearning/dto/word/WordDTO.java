package com.pirogsoft.wordslearning.dto.word;

import com.pirogsoft.wordslearning.model.Language;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class WordDTO {

    private long id;

    private String translation;

    private String name;

    private List<String> examples;

    private String comment;

    private Language language;

    private Instant createdAt;

    private Instant updatedAt;
}
