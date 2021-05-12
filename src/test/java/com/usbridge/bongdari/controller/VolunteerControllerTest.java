package com.usbridge.bongdari.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.model.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void 봉사공고등록() throws Exception {
        Volunteer volunteer = Volunteer.builder()
                .id(50L)
                .title("봉사 활동")
                .details("봉사하기")
                .time("오전10시부터")
                .contact("010-1234-5678")
                .capacity(10)
                .gender(Gender.ALL)
                .location("마포구")
                .manager("이재복")
                .startDate(LocalDate.of(2050, 5, 1))
                .endDate(LocalDate.of(2020, 5, 10))
                .createdDate(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(volunteer)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }

    @Test
    public void 봉사공고가져오기_성공() throws Exception {
        mockMvc.perform(get("/api/volunteer/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void 봉사공고가져오기_실패() throws Exception {
        mockMvc.perform(get("/api/volunteer/10000"))
                .andExpect(status().isBadRequest());
    }
}