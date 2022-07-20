package com.pirogsoft.wordslearning.service;

import com.pirogsoft.wordslearning.exception.WordAlreadyInSetException;
import com.pirogsoft.wordslearning.exception.WordNotFoundException;
import com.pirogsoft.wordslearning.exception.WordSetNotFoundException;
import com.pirogsoft.wordslearning.model.Word;
import com.pirogsoft.wordslearning.model.WordSet;
import com.pirogsoft.wordslearning.repository.WordRepository;
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

    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public List<WordSet> getAll() {
        return wordSetRepository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public WordSet getByIdWithWords(long id) {
        return getByIdWithWordsInternal(id);
    }

    @Transactional
    public long create(WordSet wordSet) {
        return wordSetRepository.save(wordSet).getId();
    }

    @Transactional
    public void updateIgnoreWords(long id, WordSet wordSet) {
        WordSet oldWordSet = getByIdWithWordsInternal(id);
        oldWordSet.setName(wordSet.getName());
        oldWordSet.setLanguage(wordSet.getLanguage());
        wordSetRepository.save(oldWordSet);
    }

    @Transactional
    public void delete(long id) {
        if (!wordSetRepository.existsById(id)) throw new WordSetNotFoundException(id);
        wordSetRepository.deleteById(id);
    }

    @Transactional
    public void createAndLinkWord(long wordSetId, Word word) {
        WordSet wordSet = getByIdWithWordsInternal(wordSetId);
        wordRepository.save(word);
        wordSet.getWords().add(word);
        wordSetRepository.save(wordSet);
    }

    @Transactional
    public void linkWord(long wordSetId, long wordId) {
        WordSet wordSet = getByIdWithWordsInternal(wordSetId);
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new WordNotFoundException(wordId));
        boolean alreadyExists = !wordSet.getWords().add(word);
        if (alreadyExists) throw new WordAlreadyInSetException(wordSetId, wordId);
    }

    private WordSet getByIdWithWordsInternal(long id) {
        return wordSetRepository.findByIdWithWord(id).orElseThrow(() -> new WordSetNotFoundException(id));
    }
}
