package com.test.example.controllertests.integration;

import com.test.example.ExampleController;
import com.test.example.ExampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;


@RunWith(SpringRunner.class)
@WebMvcTest(excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes={CommandLineRunner.class, ExampleController.class})
public class SimpleUnitTestSkipSpringSecurity {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExampleRepository exampleRepository;

    @Test
    public void verifyAllUriCalled() throws Exception {
        when(exampleRepository.findAll()).thenReturn(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/all/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(ExampleController.class))
                .andExpect(handler().methodName("getExamples"))
                .andReturn();
        System.out.println(mvcResult.getResponse());
        verify(exampleRepository).findAll();
    }


    @Test
    public void verifyUrlCalledSpecificMethodOnTheControllerWithParam() throws Exception {
        String nameParam="SomeName";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/all/param")
                .param("name", nameParam))
                .andExpect(handler().handlerType(ExampleController.class))
                .andExpect(handler().methodName("getExamplesWithParam")
        ).andReturn();
        System.out.println(mvcResult.getResponse());
        verify(exampleRepository).findExampleByName(nameParam);
    }
}
