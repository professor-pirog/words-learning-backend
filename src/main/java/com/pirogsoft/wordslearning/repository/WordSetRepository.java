package com.pirogsoft.wordslearning.repository;

import com.pirogsoft.wordslearning.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface WordSetRepository extends JpaRepository<WordSet, Long> {

    @Query("SELECT ws FROM WordSet ws LEFT JOIN ws.words WHERE ws.id = :id")
    Optional<WordSet> findByIdWithWord(long id);
}
