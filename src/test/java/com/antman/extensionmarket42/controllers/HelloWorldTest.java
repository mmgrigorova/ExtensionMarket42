package com.antman.extensionmarket42.controllers;

import com.antman.extensionmarket42.Extensionmarket42Application;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
//@SpringBootTest(classes = Extensionmarket42Application.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {Extensionmarket42Application.class}
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , properties = "spring.configuration.exclude=com.antman.extensionmarket42.configuration.AppConfiguration"
)
public class HelloWorldTest {
    private String hello;


    @Autowired
    MockMvc mockMvc;

    @Ignore
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
