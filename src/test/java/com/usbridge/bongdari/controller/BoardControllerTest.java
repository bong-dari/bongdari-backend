package com.usbridge.bongdari.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
public class BoardControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void before(WebApplicationContext wac){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("게시판 조회_city_gu_없을때")
    @Test
    void getBoardListNoCityAndGu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category").value("TOWN"))
                .andExpect(jsonPath("$.content[0].city").value("서울시"));
    }

    @DisplayName("게시판 조회")
    @Test
    void getBoardList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/2?city=서울시&gu=강동구"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category").value("TOWN"))
                .andExpect(jsonPath("$.content[0].city").value("서울시"))
                .andExpect(jsonPath("$.content[0].gu").value("강동구"));
    }
}