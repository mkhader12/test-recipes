package com.test.example.testcontainer.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {UserRepositoryIntegrationTest.Initializer.class})
public class UserRepositoryIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer resonatePostgreSQLContainer = ResonatePostgreSQLContainer.getInstance();

    public static PostgreSQLContainer genericSqlContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + resonatePostgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + resonatePostgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + resonatePostgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void test() {

    }
}