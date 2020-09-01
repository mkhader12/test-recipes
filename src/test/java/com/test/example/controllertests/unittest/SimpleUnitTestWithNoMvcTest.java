package com.test.example.controllertests.unittest;

import com.test.example.ExampleController;
import com.test.example.ExampleDto;
import com.test.example.ExampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleUnitTestWithNoMvcTest {
    @InjectMocks
    ExampleController controller;

    @Mock
    ExampleRepository repository;

    @Test
    public void testOne() {
        ExampleDto myDto = new ExampleDto();
        String myParam = "one";
        when(repository.findExampleByName(myParam)).thenReturn(myDto);
        assertEquals(myDto, controller.getExamplesWithParam(myParam));
    }
}
