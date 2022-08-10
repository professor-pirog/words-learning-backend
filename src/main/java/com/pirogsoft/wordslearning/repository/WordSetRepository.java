package com.pirogsoft.wordslearning.repository;

import com.pirogsoft.wordslearning.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WordSetRepository extends JpaRepository<WordSet, Long> {

    @Query(nativeQuery = true, value = "SELECT set_id, count(*) from word_word_set_link WHERE set_id in :setIds GROUP BY set_id")
    List<Object[]> getWordCounts(Set<Long> setIds);

    @Query("SELECT ws FROM WordSet ws LEFT JOIN ws.words WHERE ws.id = :id")
    Optional<WordSet> findByIdWithWord(long id);
}
