package com.usbridge.bongdari.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.nickname").value("jwljwl"))
//                .andExpect(jsonPath("$.contact").value("01066075331"))
//                .andExpect(jsonPath("$.category").value("TOWN"))
//                .andExpect(jsonPath("$.details").value("봉사활동 게시판 테스트 입니다."))
//                .andExpect(jsonPath("$.startDate").value("2021-05-20"))
//                .andExpect(jsonPath("$.endDate").value("2021-05-30"))
//                .andExpect(jsonPath("$.createdDate").value("2021-05-14T20:00:58"))
//                .andExpect(jsonPath("$.capacity").value(3))
//                .andExpect(jsonPath("$.city").value("서울시"))
//                .andExpect(jsonPath("$.gu").value("강동구"));
    }

    @DisplayName("게시판 조회")
    @Test
    void getBoardList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/2?city=서울시&gu=강동구"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.nickname").value("jwljwl"))
//                .andExpect(jsonPath("$.contact").value("01066075331"))
//                .andExpect(jsonPath("$.category").value("TOWN"))
//                .andExpect(jsonPath("$.details").value("봉사활동 게시판 테스트 입니다."))
//                .andExpect(jsonPath("$.startDate").value("2021-05-20"))
//                .andExpect(jsonPath("$.endDate").value("2021-05-30"))
//                .andExpect(jsonPath("$.createdDate").value("2021-05-14T20:00:58"))
//                .andExpect(jsonPath("$.capacity").value(3))
//                .andExpect(jsonPath("$.city").value("서울시"))
//                .andExpect(jsonPath("$.gu").value("강동구"));
    }

}