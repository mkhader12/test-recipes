package com.test.example.controllertests.unittest;

import com.test.example.ExampleController;
import com.test.example.ExampleDto;
import com.test.example.ExampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test")
public class SimpleUnitTestWithMockMvc {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExampleRepository repository;

    @Test
    public void testOne() throws Exception {
        ExampleDto myDto = new ExampleDto();
        String myParam = "one";
        when(repository.findExampleByName(myParam)).thenReturn(myDto);

        MockHttpServletResponse response = mockMvc.perform(get("/all/"))
                .andReturn().getResponse();

        System.out.println(response);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
