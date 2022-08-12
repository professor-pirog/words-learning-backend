package com.pirogsoft.wordslearning.web;

import com.pirogsoft.wordslearning.dto.word.WordCreateOrUpdateDTO;
import com.pirogsoft.wordslearning.dto.word.WordDTO;
import com.pirogsoft.wordslearning.dto.word.WordListDTO;
import com.pirogsoft.wordslearning.mapper.WordMapper;
import com.pirogsoft.wordslearning.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/words")
@CrossOrigin //to tests
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    private final WordMapper wordMapper;

    @GetMapping
    public WordListDTO getList(@RequestParam(required = false) String name, Principal principal) {
        List<WordDTO> wordDTOs = wordService.getAll(name, principal.getName()).stream().map(wordMapper::mapToDTO).collect(Collectors.toList());
        return WordListDTO.builder().content(wordDTOs).build();
    }

    @GetMapping("/{id}")
    public WordDTO getById(@PathVariable long id, Principal principal) {
        return wordMapper.mapToDTO(wordService.getById(id, principal.getName()));
    }

    @PostMapping
    public long create(@RequestBody WordCreateOrUpdateDTO wordDTO, Principal principal) {
        return wordService.create(wordMapper.mapToDomain(wordDTO, principal.getName()));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody WordCreateOrUpdateDTO wordDTO, Principal principal) {
        wordService.update(id, wordMapper.mapToDomain(wordDTO, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id, Principal principal) {
        wordService.delete(id, principal.getName());
    }
}
