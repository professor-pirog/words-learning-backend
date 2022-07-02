package com.pirogsoft.wordslearning.exception;

public class WordSetNotFoundException extends RuntimeException {

    public WordSetNotFoundException(long id) {
        super("Word set with id " + id + " not found");
    }
}
