package com.test.example.testcontainer.tests;

import com.test.example.db.User;
import com.test.example.db.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {DatabaseIntegrationTest.Initializer.class})
public class DatabaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Environment environment;

    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres")
            .withDatabaseName("postgres")
            .withUsername("integrationUser")
            .withPassword("testPass");

    @LocalServerPort
    private Integer port;

    @Test
    public void testWriteToDb_afterBoot_shouldHaveEntries() {
        List<User> all = userRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(6);
        Assertions.assertThat(all.get(0).getFirstName()).isEqualTo("First");
        Assertions.assertThat(all.get(0).getLastName()).isEqualTo("Last");
    }

    @Test
    public void testRetrievalOfUser() {
        User anUser = userRepository.findByFirstName("Peter");
        assertNotNull(anUser);
        assertEquals("peter", anUser.getFirstName().toLowerCase());
        assertEquals("pan", anUser.getLastName().toLowerCase());

    }

    @Test
    public void testGet_returns_200_with_expected_users() {
        String baseUrl="http://localhost:"+port;
        TestRestTemplate testRestTemplate = new TestRestTemplate("test", "password", TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
        ResponseEntity<String> response = testRestTemplate.withBasicAuth("test","password").getForEntity(baseUrl + "/v1/users", String.class);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String requestJson="{}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestJson, requestHeaders);

        ResponseEntity<List<User>> responseEntity = testRestTemplate.exchange(
                baseUrl+ "/v1/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<User>>(){}
        );
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}