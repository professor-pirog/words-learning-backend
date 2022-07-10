package com.pirogsoft.wordslearning.service;

import com.pirogsoft.wordslearning.exception.WordNotFoundException;
import com.pirogsoft.wordslearning.model.Word;
import com.pirogsoft.wordslearning.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public List<Word> getAll() {
        return wordRepository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public Word getById(long id) {
        return getByIdInternal(id);
    }

    @Transactional
    public long create(Word word) {
        return wordRepository.save(word).getId();
    }

    @Transactional
    public void update(long id, Word word) {
        Word oldWord = getByIdInternal(id);
        oldWord.setName(word.getName());
        oldWord.setTranslation(word.getTranslation());
        oldWord.setExamples(word.getExamples());
        oldWord.setComment(word.getComment());
        wordRepository.save(oldWord);
    }

    @Transactional
    public void delete(long id) {
        if(!wordRepository.existsById(id)) throw new WordNotFoundException(id);
        wordRepository.deleteById(id);
    }

    private Word getByIdInternal(long id) {
        return wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException(id));
    }
}
