package com.usbridge.bongdari.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usbridge.bongdari.controller.dto.BoardRequestDto;
import com.usbridge.bongdari.model.enums.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
public class BoardControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before(WebApplicationContext wac){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("게시판 조회")
    @Test
    void getBoardList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board?category=TOWN&city=서울시&gu=강동구"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category").value("TOWN"))
                .andExpect(jsonPath("$.content[0].city").value("서울시"))
                .andExpect(jsonPath("$.content[0].gu").value("강동구"));
    }

    @DisplayName("게시판 조회_city_gu_없을때")
    @Test
    void getBoardListNoCityAndGu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board?category=TOWN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category").value("TOWN"))
                .andExpect(jsonPath("$.content[0].city").value("서울시"));
    }

    @DisplayName("게시판 등록")
    @Test
    void createBoard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BoardRequestDto.builder()
                        .capacity(11)
                        .category(Category.TOGETHER)
                        .contact("01055556666")
                        .details("")
                        .startDate(LocalDate.of(2021, 5, 15))
                        .endDate(LocalDate.of(2021, 5, 20))
                        .createdDate(LocalDateTime.of(2021, 5, 10, 13, 0))
                        .city("경기도")
                        .gu("화성시")
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity").value(11))
                .andExpect(jsonPath("$.category").value("TOGETHER"))
                .andExpect(jsonPath("$.city").value("경기도"))
                .andExpect(jsonPath("$.gu").value("화성시"));
    }
}