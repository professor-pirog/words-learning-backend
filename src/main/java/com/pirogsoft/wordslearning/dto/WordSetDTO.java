package com.pirogsoft.wordslearning.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordSetDTO {

    private long id;

    private String name;
}
