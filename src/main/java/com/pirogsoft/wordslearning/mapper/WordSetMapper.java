package com.pirogsoft.wordslearning.mapper;

import com.pirogsoft.wordslearning.dto.wordset.WordSetCreateOrUpdateDTO;
import com.pirogsoft.wordslearning.dto.wordset.WordSetDTO;
import com.pirogsoft.wordslearning.dto.wordset.WordSetDetailDTO;
import com.pirogsoft.wordslearning.model.WordSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WordSetMapper {

    private final WordMapper wordMapper;

    public WordSet mapToDomain(WordSetCreateOrUpdateDTO wordSetDTO) {
        WordSet wordSet = new WordSet();
        wordSet.setName(wordSetDTO.getName());
        wordSet.setLanguage(wordSetDTO.getLanguage());
        return wordSet;
    }

    public WordSetDTO mapToDTO(WordSet wordSet, int wordCount) {
        return WordSetDTO
                .builder()
                .id(wordSet.getId())
                .name(wordSet.getName())
                .language(wordSet.getLanguage())
                .wordCount(wordCount)
                .createdAt(wordSet.getCreatedAt())
                .updatedAt(wordSet.getUpdatedAt())
                .build();
    }

    public WordSetDetailDTO mapToDetailDTO(WordSet wordSet) {
        return WordSetDetailDTO
                .builder()
                .id(wordSet.getId())
                .name(wordSet.getName())
                .words(wordSet.getWords().stream().map(wordMapper::mapToDTO).collect(Collectors.toSet()))
                .language(wordSet.getLanguage())
                .createdAt(wordSet.getCreatedAt())
                .updatedAt(wordSet.getUpdatedAt())
                .build();
    }
}
