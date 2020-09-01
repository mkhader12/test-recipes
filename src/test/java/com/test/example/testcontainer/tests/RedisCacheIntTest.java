package com.test.example.testcontainer.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedisCacheIntTest {
    private RedisBackedCache underTest;

    @Rule
    public GenericContainer redis = new GenericContainer("redis:5.0.3-alpine")
            .withExposedPorts(6379);

    @Before
    public void setUp() {
        String redisHost = redis.getHost();
        Integer redisPort = redis.getFirstMappedPort();
        underTest = new RedisBackedCache(redisHost, redisPort);
    }

    @Test
    public void testSimplePutAndGet() {
        underTest.put("test", "example");

        String retrieved = underTest.get("test");
        assertEquals("example", retrieved);
    }
}