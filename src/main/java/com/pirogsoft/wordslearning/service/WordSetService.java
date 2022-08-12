package com.pirogsoft.wordslearning.service;

import com.pirogsoft.wordslearning.exception.WordAlreadyInSetException;
import com.pirogsoft.wordslearning.exception.WordNotFoundException;
import com.pirogsoft.wordslearning.exception.WordSetNotFoundException;
import com.pirogsoft.wordslearning.model.Word;
import com.pirogsoft.wordslearning.model.WordSet;
import com.pirogsoft.wordslearning.repository.WordRepository;
import com.pirogsoft.wordslearning.repository.WordSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordSetService {

    private final WordSetRepository wordSetRepository;

    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public List<Pair<WordSet, Integer>> getAllWithWordCount(String username) {
        List<WordSet> wordSetList = wordSetRepository.findAllByUsernameOrderById(username);
        List<Object[]> counts = wordSetRepository.getWordCounts(wordSetList.stream().map(WordSet::getId).collect(Collectors.toSet()));
        Map<Long, Integer> countsMap = counts
                .stream()
                .collect(
                        Collectors.toMap(
                                objects -> Long.valueOf((Integer)objects[0]),
                                objects -> ((BigInteger) objects[1]).intValue()
                        )
                );
        return wordSetList.stream().map(wordSet -> Pair.of(wordSet, countsMap.getOrDefault(wordSet.getId(), 0))).toList();

    }

    @Transactional(readOnly = true)
    public WordSet getByIdWithWords(long id, String user) {
        return getByIdWithWordsInternal(id, user);
    }

    @Transactional
    public long create(WordSet wordSet) {
        return wordSetRepository.save(wordSet).getId();
    }

    @Transactional
    public void updateIgnoreWords(long id, WordSet wordSet) {
        WordSet oldWordSet = getByIdWithWordsInternal(id, wordSet.getUsername());
        oldWordSet.setName(wordSet.getName());
        oldWordSet.setLanguage(wordSet.getLanguage());
        wordSetRepository.save(oldWordSet);
    }

    @Transactional
    public void delete(long id, String user) {
        WordSet wordSet = wordSetRepository.findById(id).orElseThrow(() -> new WordSetNotFoundException(id));
        if(!wordSet.getUsername().equals(user)) throw new AccessDeniedException("You don't have access to this wordSet.");
        wordSetRepository.deleteById(id);
    }

    @Transactional
    public void createAndLinkWord(long wordSetId, Word word, String user) {
        WordSet wordSet = getByIdWithWordsInternal(wordSetId, user);
        wordRepository.save(word);
        wordSet.getWords().add(word);
        wordSetRepository.save(wordSet);
    }

    @Transactional
    public void linkWord(long wordSetId, long wordId, String user) {
        WordSet wordSet = getByIdWithWordsInternal(wordSetId, user);
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new WordNotFoundException(wordId));
        boolean alreadyExists = !wordSet.getWords().add(word);
        if (alreadyExists) throw new WordAlreadyInSetException(wordSetId, wordId);
    }

    @Transactional
    public void deleteWordFromWordSet(long wordSetId, long wordId, String user) {
        WordSet wordSet = getByIdWithWordsInternal(wordSetId, user);
        Word word = wordSet.getWords().stream().filter(it -> it.getId() == wordId).findFirst().orElseThrow(); //TODO: Custom exception
        wordSet.getWords().remove(word);
        wordSetRepository.save(wordSet);
    }

    private WordSet getByIdWithWordsInternal(long id, String user) {
        WordSet wordSet =  wordSetRepository.findByIdWithWord(id).orElseThrow(() -> new WordSetNotFoundException(id));
        if(!wordSet.getUsername().equals(user)) throw new AccessDeniedException("You don't have access to this wordSet.");
        return wordSet;
    }
}
