package com.test.example;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExampleRepository extends JpaRepository<ExampleDto, Long> {
    public List<ExampleDto> findAll();

    ExampleDto findExampleByName(String name);
}
