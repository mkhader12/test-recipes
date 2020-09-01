package com.test.example.testcontainer.tests;

import redis.clients.jedis.Jedis;

public class RedisBackedCache implements Cache {

    private static final String TEST_CACHE = "TestCache";
    private final Jedis jedis;

    public RedisBackedCache(Jedis jedis, String cacheName) {
        this.jedis = jedis;
    }

    public RedisBackedCache(String localhost, int port) {
        this.jedis = new Jedis(localhost, port);
    }

    @Override
    public void put(String key, String value) {
        this.jedis.hset(TEST_CACHE, key, value);
    }

    @Override
    public String get(String key) {
        return this.jedis.hget(TEST_CACHE, key);
    }

}