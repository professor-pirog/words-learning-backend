package com.pirogsoft.wordslearning.mapper;

import com.pirogsoft.wordslearning.dto.WordDTO;
import com.pirogsoft.wordslearning.model.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WordMapper {

    public WordDTO mapToDTO(Word word) {
        return WordDTO
                .builder()
                .name(word.getName())
                .translation(word.getTranslation())
                .examples(new ArrayList<>(word.getExamples()))
                .build();
    }

}
