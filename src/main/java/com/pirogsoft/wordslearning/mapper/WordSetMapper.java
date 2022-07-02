package com.pirogsoft.wordslearning.mapper;

import com.pirogsoft.wordslearning.dto.WordSetDTO;
import com.pirogsoft.wordslearning.dto.WordSetDetailDTO;
import com.pirogsoft.wordslearning.model.WordSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WordSetMapper {

    private final WordMapper wordMapper;

    public WordSetDTO mapToDTO(WordSet wordSet) {
        return WordSetDTO
                .builder()
                .id(wordSet.getId())
                .name(wordSet.getName())
                .build();
    }

    public WordSetDetailDTO mapToDetailDTO(WordSet wordSet) {
        return WordSetDetailDTO
                .builder()
                .id(wordSet.getId())
                .name(wordSet.getName())
                .words(wordSet.getWords().stream().map(wordMapper::mapToDTO).collect(Collectors.toSet()))
                .build();
    }
}
