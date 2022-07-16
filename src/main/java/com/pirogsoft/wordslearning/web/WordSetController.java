package com.pirogsoft.wordslearning.web;

import com.pirogsoft.wordslearning.dto.word.WordCreateOrUpdateDTO;
import com.pirogsoft.wordslearning.dto.wordset.WordSetCreateOrUpdateDTO;
import com.pirogsoft.wordslearning.dto.wordset.WordSetDTO;
import com.pirogsoft.wordslearning.dto.wordset.WordSetDetailDTO;
import com.pirogsoft.wordslearning.mapper.WordMapper;
import com.pirogsoft.wordslearning.mapper.WordSetMapper;
import com.pirogsoft.wordslearning.service.WordSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/word-sets")
@CrossOrigin //for testing
@RequiredArgsConstructor
public class WordSetController {

    private final WordSetMapper wordSetMapper;

    private final WordMapper wordMapper;

    private final WordSetService wordSetService;

    @GetMapping
    public List<WordSetDTO> getList() {
        return wordSetService.getAll().stream().map(wordSetMapper::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WordSetDetailDTO get(@PathVariable long id) {
        return wordSetMapper.mapToDetailDTO(wordSetService.getByIdWithWords(id));
    }

    @PostMapping
    public long create(@RequestBody WordSetCreateOrUpdateDTO wordSetDTO) {
        return wordSetService.create(wordSetMapper.mapToDomain(wordSetDTO));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody WordSetCreateOrUpdateDTO wordSetDTO) {
        wordSetService.updateIgnoreWords(id, wordSetMapper.mapToDomain(wordSetDTO));
    }

    @PostMapping("/{id}/word")
    public void createWord(@PathVariable long id, @RequestBody WordCreateOrUpdateDTO word) {
        wordSetService.createAndLinkWord(id, wordMapper.mapToDomain(word));
    }

    @PostMapping("/{id}/word-link")
    public void attachWord(@PathVariable long id, @RequestBody long wordId) {
        wordSetService.linkWord(id, wordId);
    }

    @DeleteMapping("/{id}")
    public void deleteWordSet(@PathVariable long id) {
        wordSetService.delete(id);
    }
}
