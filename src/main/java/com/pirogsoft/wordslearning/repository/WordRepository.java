package com.pirogsoft.wordslearning.repository;

import com.pirogsoft.wordslearning.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
