package com.pirogsoft.wordslearning.mapper;

import com.pirogsoft.wordslearning.dto.WordDTO;
import com.pirogsoft.wordslearning.model.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordMapper {

    public WordDTO mapToDTO(Word word) {
        List<String> examples = word.getExamples();
        List<String> mappedExamples = examples != null ? new ArrayList<>(examples) : new ArrayList<>();
        return WordDTO
                .builder()
                .name(word.getName())
                .translation(word.getTranslation())
                .examples(mappedExamples)
                .build();
    }

}
