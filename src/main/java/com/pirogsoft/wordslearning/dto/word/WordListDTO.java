package com.pirogsoft.wordslearning.dto.word;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WordListDTO {

    private List<WordDTO> content;
}
