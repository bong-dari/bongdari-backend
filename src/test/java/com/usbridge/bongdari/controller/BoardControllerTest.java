package com.usbridge.bongdari.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usbridge.bongdari.controller.dto.BoardRequestDto;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @DisplayName("게시글 조회")
    @Test
    void getBoardList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board?category=TOWN&city=서울시&gu=강동구"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category").value("TOWN"))
                .andExpect(jsonPath("$.content[0].city").value("서울시"))
                .andExpect(jsonPath("$.content[0].gu").value("강동구"));
    }

    @DisplayName("게시글 조회_city_gu_없을때")
    @Test
    void getBoardListNoCityAndGu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board?category=TOWN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category").value("TOWN"))
                .andExpect(jsonPath("$.content[0].city").value("서울시"));
    }

    @DisplayName("게시글 등록")
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
                        .city("경기도")
                        .gu("화성시")
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity").value(11))
                .andExpect(jsonPath("$.category").value("TOGETHER"))
                .andExpect(jsonPath("$.city").value("경기도"))
                .andExpect(jsonPath("$.gu").value("화성시"));
    }

    @DisplayName("게시글 삭제")
    @Test
    void deleteBoard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/board/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity").value(12))
                .andExpect(jsonPath("$.category").value("TOWN"))
                .andExpect(jsonPath("$.contact").value("01044445555"))
                .andExpect(jsonPath("$.details").value("4. 게시판 테스트입니다"))
                .andExpect(jsonPath("$.startDate").value("2021-11-22"))
                .andExpect(jsonPath("$.endDate").value("2021-12-22"))
                .andExpect(jsonPath("$.city").value("경기도"))
                .andExpect(jsonPath("$.gu").value("양평군"))
                .andExpect(jsonPath("$.member.id").value(2L));
    }

    @DisplayName("게시글 삭제_게시글 존재 x")
    @Test
    void deleteBoard_ResourcesNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/board/15"))
                .andExpect((exception) -> assertTrue(exception.getResolvedException().getClass().isAssignableFrom(ResourceNotFoundException.class)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("게시글 수정")
    @Test
    void updateBoard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BoardRequestDto.builder()
                        .id(1L)
                        .capacity(11)
                        .city("경기도")
                        .gu("화성시")
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.capacity").value(11))
                .andExpect(jsonPath("$.city").value("경기도"))
                .andExpect(jsonPath("$.gu").value("화성시"));
    }

    @DisplayName("게시글 수정_게시글 존재 x")
    @Test
    void updateBoard_ResourceNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BoardRequestDto.builder()
                        .id(15L)
                        .capacity(11)
                        .city("경기도")
                        .gu("화성시")
                        .build())))
                .andExpect((exception) -> assertTrue(exception.getResolvedException().getClass().isAssignableFrom(ResourceNotFoundException.class)))
                .andExpect(status().isNotFound());
    }
}