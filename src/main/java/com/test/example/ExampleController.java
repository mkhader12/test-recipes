package com.test.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/all")
public class ExampleController {

    private ExampleRepository exampleRepository;

    ExampleController(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @GetMapping("/")
    public List<ExampleDto> getExamples() {
        return exampleRepository.findAll();
    }

    @GetMapping("/param")
    public ExampleDto getExamplesWithParam(@RequestParam String name) {
        return exampleRepository.findExampleByName(name);
    }
}
