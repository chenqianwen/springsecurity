package com.example.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void findSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username","小河")
                .param("age","1")
                .param("size","15")
                .param("page","3")
                .param("sort","age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findOne() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void saveSuccess() throws Exception{
        String content = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
