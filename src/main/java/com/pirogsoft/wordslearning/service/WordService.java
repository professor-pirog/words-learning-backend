package com.pirogsoft.wordslearning.service;

import com.pirogsoft.wordslearning.exception.WordNotFoundException;
import com.pirogsoft.wordslearning.model.Word;
import com.pirogsoft.wordslearning.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public List<Word> getAll(String name, String username) {
        if (name == null) {
            return wordRepository.findAllByUsernameOrderById(username);
        } else {
            return wordRepository.findAllByNameAndUsernameOrderById(name, username);
        }
    }

    @Transactional(readOnly = true)
    public Word getById(long id, String user) {
        return getByIdInternal(id, user);
    }

    @Transactional
    public long create(Word word) {
        return wordRepository.save(word).getId();
    }

    @Transactional
    public void update(long id, Word word) {
        Word oldWord = getByIdInternal(id, word.getUsername());
        oldWord.setName(word.getName());
        oldWord.setTranslation(word.getTranslation());
        oldWord.setExamples(word.getExamples());
        oldWord.setComment(word.getComment());
        oldWord.setLanguage(word.getLanguage());
        wordRepository.save(oldWord);
    }

    @Transactional
    public void delete(long id, String user) {
        getByIdInternal(id, user);
        wordRepository.deleteById(id);
    }

    private Word getByIdInternal(long id, String user) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        if (!word.getUsername().equals(user)) {
            throw new AccessDeniedException("You don't have access to this word.");
        }
        return word;
    }
}
