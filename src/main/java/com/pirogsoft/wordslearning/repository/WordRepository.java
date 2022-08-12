package com.pirogsoft.wordslearning.repository;

import com.pirogsoft.wordslearning.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByUsernameOrderById(String username);

    List<Word> findAllByNameAndUsernameOrderById(String name, String username);

}
