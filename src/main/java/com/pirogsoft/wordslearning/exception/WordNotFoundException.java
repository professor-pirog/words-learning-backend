package com.pirogsoft.wordslearning.exception;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(long id) {
        super("Word with id " + id + " not found");
    }
}
