package com.pirogsoft.wordslearning.dto.wordset;

import com.pirogsoft.wordslearning.model.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordSetCreateOrUpdateDTO {

    private String name;

    private Language language;
}
