package com.test.example.testcontainer.tests;

import java.util.Optional;

public interface Cache {

    void put(String key, String value);

    String get(String key);
}