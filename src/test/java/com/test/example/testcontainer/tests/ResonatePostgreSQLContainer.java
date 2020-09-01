package com.test.example.testcontainer.tests;

import org.testcontainers.containers.PostgreSQLContainer;

public class ResonatePostgreSQLContainer extends PostgreSQLContainer<ResonatePostgreSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static ResonatePostgreSQLContainer container;

    private ResonatePostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static ResonatePostgreSQLContainer getInstance() {
        if (container == null) {
            container = new ResonatePostgreSQLContainer();
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