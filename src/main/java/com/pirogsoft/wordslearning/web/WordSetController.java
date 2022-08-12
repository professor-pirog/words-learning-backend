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

import java.security.Principal;
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
    public List<WordSetDTO> getList(Principal principal) {
        return wordSetService
                .getAllWithWordCount(principal.getName())
                .stream()
                .map(pair -> wordSetMapper.mapToDTO(pair.getFirst(), pair.getSecond()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WordSetDetailDTO get(@PathVariable long id, Principal principal) {
        return wordSetMapper.mapToDetailDTO(wordSetService.getByIdWithWords(id, principal.getName()));
    }

    @PostMapping
    public long create(@RequestBody WordSetCreateOrUpdateDTO wordSetDTO, Principal principal) {
        return wordSetService.create(wordSetMapper.mapToDomain(wordSetDTO, principal.getName()) );
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody WordSetCreateOrUpdateDTO wordSetDTO, Principal principal) {
        wordSetService.updateIgnoreWords(id, wordSetMapper.mapToDomain(wordSetDTO, principal.getName()) );
    }

    @PostMapping("/{id}/words")
    public void createWord(@PathVariable long id, @RequestBody WordCreateOrUpdateDTO word, Principal principal) {
        wordSetService.createAndLinkWord(id, wordMapper.mapToDomain(word, principal.getName()), principal.getName());
    }

    @PostMapping("/{id}/word-link")
    public void attachWord(@PathVariable long id, @RequestBody long wordId, Principal principal) {
        wordSetService.linkWord(id, wordId, principal.getName());
    }

    @DeleteMapping("/{id}/words/{wordId}")
    public void deleteWordFromSet(@PathVariable long id, @PathVariable long wordId, Principal principal) {
        wordSetService.deleteWordFromWordSet(id, wordId, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteWordSet(@PathVariable long id, Principal principal) {
        wordSetService.delete(id, principal.getName());
    }
}
