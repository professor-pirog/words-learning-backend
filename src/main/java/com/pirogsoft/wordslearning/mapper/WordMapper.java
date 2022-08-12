package com.pirogsoft.wordslearning.mapper;

import com.pirogsoft.wordslearning.dto.word.WordCreateOrUpdateDTO;
import com.pirogsoft.wordslearning.dto.word.WordDTO;
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
                .id(word.getId())
                .name(word.getName())
                .translation(word.getTranslation())
                .examples(mappedExamples)
                .comment(word.getComment())
                .language(word.getLanguage())
                .createdAt(word.getCreatedAt())
                .updatedAt(word.getUpdatedAt())
                .build();
    }

    public Word mapToDomain(WordCreateOrUpdateDTO wordDTO, String username) {
        Word word = new Word();
        word.setTranslation(wordDTO.getTranslation());
        word.setName(wordDTO.getName());
        word.setExamples(wordDTO.getExamples());
        word.setComment(wordDTO.getComment());
        word.setLanguage(wordDTO.getLanguage());
        word.setUsername(username);
        return word;
    }
}
