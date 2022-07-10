package com.pirogsoft.wordslearning.exception;

public class WordAlreadyInSetException extends RuntimeException {

    public WordAlreadyInSetException(long setId, long wordId) {
        super("word with id " + wordId + " already in set with id " + setId);
    }
}
