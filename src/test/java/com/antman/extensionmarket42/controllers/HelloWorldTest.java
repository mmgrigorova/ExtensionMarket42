package com.antman.extensionmarket42.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldTest {
    private String hello;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloWorld_whenHello_shouldStatus200() throws Exception {
        ResultActions expect = mockMvc.perform(
                get("/helloworld")
        )
                .andDo(print())
                .andExpect(status().isOk());

        expect.andExpect(content().string("Hello World"));
    }
}
