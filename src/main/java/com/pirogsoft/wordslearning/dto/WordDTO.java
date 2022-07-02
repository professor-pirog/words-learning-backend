package com.pirogsoft.wordslearning.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WordDTO {

    private long id;

    private String translation;

    private String name;

    private List<String> examples;
}
