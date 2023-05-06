package com.pirogsoft.wordslearning.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

public class WordsLearningPostgresqlContainer extends PostgreSQLContainer<WordsLearningPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";

    private static WordsLearningPostgresqlContainer container;


    private WordsLearningPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public synchronized static WordsLearningPostgresqlContainer getInstance() {
        if (container == null) {
            container = new WordsLearningPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
