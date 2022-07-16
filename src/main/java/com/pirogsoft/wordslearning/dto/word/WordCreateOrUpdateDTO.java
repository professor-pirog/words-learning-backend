package com.pirogsoft.wordslearning.dto.word;

import com.pirogsoft.wordslearning.model.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordCreateOrUpdateDTO {

    private String translation;

    private String name;

    private List<String> examples;

    private String comment;

    private Language language;
}
