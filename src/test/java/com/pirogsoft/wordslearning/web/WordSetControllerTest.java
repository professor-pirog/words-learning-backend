package com.pirogsoft.wordslearning.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pirogsoft.wordslearning.AbstractIntegrationTest;
import com.pirogsoft.wordslearning.dto.WordDTO;
import com.pirogsoft.wordslearning.dto.WordSetDTO;
import com.pirogsoft.wordslearning.dto.WordSetDetailDTO;
import com.pirogsoft.wordslearning.dto.error.ErrorDTO;
import com.pirogsoft.wordslearning.dto.error.ErrorType;
import com.pirogsoft.wordslearning.model.Word;
import com.pirogsoft.wordslearning.model.WordSet;
import com.pirogsoft.wordslearning.repository.WordRepository;
import com.pirogsoft.wordslearning.repository.WordSetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

public class WordSetControllerTest extends AbstractIntegrationTest {

    private static final String BASE_URL = "/word-sets";

    @Autowired
    private WordSetRepository wordSetRepository;

    @Autowired
    private WordRepository wordRepository;


    @AfterEach
    public void clenDb() {
        wordSetRepository.deleteAll();
        wordRepository.deleteAll();
    }

    @Test
    public void getListTest() throws Exception {
        WordSet wordSetOne = wordSetRepository.save(createWordSetOne());
        WordSet wordSetTwo = wordSetRepository.save(createWordSetTwo());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk())
                .andReturn();
        List<WordSetDTO> actual = getResponse(result, new TypeReference<>() {
        });
        Assertions.assertThat(actual.size()).isEqualTo(2);
        Assertions.assertThat(actual.get(0).getName()).isEqualTo(WordSetOne.NAME);
        Assertions.assertThat(actual.get(0).getId()).isEqualTo(wordSetOne.getId());
        Assertions.assertThat(actual.get(1).getName()).isEqualTo(WordSetTwo.NAME);
        Assertions.assertThat(actual.get(1).getId()).isEqualTo(wordSetTwo.getId());
    }

    @Test
    public void getWordSetTest() throws Exception {
        Word wordOne = wordRepository.save(createWordOne());
        Word wordTwo = wordRepository.save(createWordTwo());
        Word wordThree = wordRepository.save(createWordThree());
        WordSet wordSetOne = createWordSetOne();
        wordSetOne.setWords(new HashSet<>());
        wordSetOne.getWords().add(wordOne);
        wordSetOne.getWords().add(wordTwo);
        wordSetOne.getWords().add(wordThree);
        WordSet wordSetTwo = createWordSetTwo();
        wordSetTwo.setWords(new HashSet<>());
        wordSetTwo.getWords().add(wordThree);
        WordSet savedWordSetOne = wordSetRepository.save(wordSetOne);
        WordSet savedWordSetTwo = wordSetRepository.save(wordSetTwo);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedWordSetOne.getId()))
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk())
                .andReturn();
        WordSetDetailDTO actualOne = getResponse(result, WordSetDetailDTO.class);

        Assertions.assertThat(actualOne.getId()).isEqualTo(savedWordSetOne.getId());
        Assertions.assertThat(actualOne.getName()).isEqualTo(WordSetOne.NAME);
        Set<WordDTO> wordsSetOne =  actualOne.getWords();
        Assertions.assertThat(wordsSetOne.size()).isEqualTo(3);
        Optional<WordDTO> wordDTOOneOptional = findById(wordsSetOne, wordOne.getId());
        Assertions.assertThat(wordDTOOneOptional.isPresent()).isTrue();
        WordDTO wordDTOOne = wordDTOOneOptional.get();
        Assertions.assertThat(wordDTOOne.getId()).isEqualTo(wordOne.getId());
        Assertions.assertThat(wordDTOOne.getName()).isEqualTo(WordOne.NAME);
        Assertions.assertThat(wordDTOOne.getTranslation()).isEqualTo(WordOne.TRANSLATION);
        List<String> wordOneExamples = wordDTOOne.getExamples();
        Assertions.assertThat(wordOneExamples.size()).isEqualTo(2);
        Assertions.assertThat(wordOneExamples.get(0)).isEqualTo(WordOne.EXAMPLE_ONE);
        Assertions.assertThat(wordOneExamples.get(1)).isEqualTo(WordOne.EXAMPLE_TWO);
    }

    @Test
    public void wordSetNotFoundExceptionTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1"))
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isNotFound())
                .andReturn();
        ErrorDTO actual = getResponse(result, ErrorDTO.class);
        Assertions.assertThat(actual.getErrorType()).isEqualTo(ErrorType.WORD_SET_NOT_FOUND);
        Assertions.assertThat(actual.getTechnicalDescription()).isEqualTo("Word set with id 1 not found");
        Assertions.assertThat(actual.getMessage()).isEqualTo("Word set not found");
        Assertions.assertThat(actual.getTimestamp()).isNotNull();
    }

    @Test
    public void getEmptyWordSetTest() throws Exception {
        WordSet savedWordSetOne = wordSetRepository.save(createWordSetOne());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedWordSetOne.getId()))
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk())
                .andReturn();
        WordSetDetailDTO actualOne = getResponse(result, WordSetDetailDTO.class);
        Assertions.assertThat(actualOne.getId()).isEqualTo(savedWordSetOne.getId());
        Assertions.assertThat(actualOne.getName()).isEqualTo(WordSetOne.NAME);
        Assertions.assertThat(actualOne.getWords()).isEmpty();

    }

    private Optional<WordDTO> findById(Set<WordDTO> wordSet, long id) {
        return wordSet.stream().filter(wordDTO -> wordDTO.getId() == id).findAny();
    }

    private Word createWordOne() {
        return createWord(WordOne.NAME, WordOne.TRANSLATION, Arrays.asList(WordOne.EXAMPLE_ONE, WordOne.EXAMPLE_TWO));
    }

    private Word createWordTwo() {
        return createWord(WordTwo.NAME, WordTwo.TRANSLATION, Arrays.asList(WordTwo.EXAMPLE_ONE, WordTwo.EXAMPLE_TWO));
    }

    private Word createWordThree() {
        return createWord(WordThree.NAME, WordThree.TRANSLATION, Arrays.asList(WordThree.EXAMPLE_ONE, WordThree.EXAMPLE_TWO));
    }

    private Word createWord(String name, String translation, List<String> examples) {
        Word word = new Word();
        word.setName(name);
        word.setTranslation(translation);
        word.setExamples(examples);
        return word;
    }

    private WordSet createWordSetOne() {
        WordSet wordSet = new WordSet();
        wordSet.setName(WordSetOne.NAME);
        return wordSet;
    }

    private WordSet createWordSetTwo() {
        WordSet wordSet = new WordSet();
        wordSet.setName(WordSetTwo.NAME);
        return wordSet;
    }

    private class WordSetOne {
        public static String NAME = "Word set one";
    }

    private class WordSetTwo {
        public static String NAME = "Word set one";
    }

    private class WordOne {
        public static String NAME = "mother";
        public static String TRANSLATION = "мать";
        public static String EXAMPLE_ONE = "My mother like apples.";
        public static String EXAMPLE_TWO = "I visit my mother once a week.";
    }

    private class WordTwo {
        public static String NAME = "further";
        public static String TRANSLATION = "отец";
        public static String EXAMPLE_ONE = "My further like apples.";
        public static String EXAMPLE_TWO = "I visit my further once a week.";
    }

    private class WordThree {
        public static String NAME = "daughter";
        public static String TRANSLATION = "дочь";
        public static String EXAMPLE_ONE = "My daughter like apples.";
        public static String EXAMPLE_TWO = "I visit my daughter once a week.";
    }
}
