package com.pirogsoft.wordslearning.dto.wordset;

import com.pirogsoft.wordslearning.dto.word.WordDTO;
import com.pirogsoft.wordslearning.model.Language;
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

    private Language language;

    private Instant createdAt;

    private Instant updatedAt;
}
