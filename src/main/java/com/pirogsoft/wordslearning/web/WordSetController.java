package com.pirogsoft.wordslearning.web;

import com.pirogsoft.wordslearning.dto.WordSetDTO;
import com.pirogsoft.wordslearning.dto.WordSetDetailDTO;
import com.pirogsoft.wordslearning.mapper.WordSetMapper;
import com.pirogsoft.wordslearning.service.WordSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/word-sets")
@CrossOrigin
@RequiredArgsConstructor
public class WordSetController {

    private final WordSetMapper wordSetMapper;

    private final WordSetService wordSetService;

    @GetMapping
    public List<WordSetDTO> getList() {
        return wordSetService.getList().stream().map(wordSetMapper::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WordSetDetailDTO get(@PathVariable long id) {
        return wordSetMapper.mapToDetailDTO(wordSetService.getById(id));
    }
}
