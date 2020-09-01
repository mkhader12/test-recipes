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

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

/**
 *
 * Purpose of this test to verify a URL called a specific method in the controller
 *
 * Controller used : Mocked Controller
 * Do we need to mock the controller dependencies : No
 *
 * This performs call to the mock controller and verifies the url called right controller and right method
 */


@RunWith(SpringRunner.class)
@WebMvcTest(value = ExampleController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes={CommandLineRunner.class})
public class SimpleUnitTestSkipSpringSecurityMockController {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ExampleController remoteController;

    @Test
    public void verifyUrlCalledSpecificMethodOnTheMockController() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/all/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse());
        verify(remoteController).getExamples();
    }

    @Test
    public void verifyUrlCalledSpecificMethodOnTheMockControllerWithParam() throws Exception {
        String nameParam="SomeName";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/all/param")
                .param("name", nameParam)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse());
        verify(remoteController).getExamplesWithParam(nameParam);
    }
}
