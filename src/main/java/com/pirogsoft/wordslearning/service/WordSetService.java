package com.pirogsoft.wordslearning.service;

import com.pirogsoft.wordslearning.exception.WordSetNotFoundException;
import com.pirogsoft.wordslearning.model.WordSet;
import com.pirogsoft.wordslearning.repository.WordSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordSetService {

    private final WordSetRepository wordSetRepository;

    @Transactional(readOnly = true)
    public List<WordSet> getList() {
        return wordSetRepository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public WordSet getById(long id) {
        return wordSetRepository.findByIdWithWord(id).orElseThrow(() -> new WordSetNotFoundException(id));
    }

}
