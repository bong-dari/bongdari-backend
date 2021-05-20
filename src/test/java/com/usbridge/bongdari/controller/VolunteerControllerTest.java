package com.usbridge.bongdari.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.model.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void before(WebApplicationContext was) {
        mockMvc = MockMvcBuilders.webAppContextSetup(was)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("정상적으로 공고 등록")
    public void createVolunteer() throws Exception {
        VolunteerDto volunteerDto = givenVolunteerDto();

        mockMvc.perform(post("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(volunteerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists());
    }

    @Test
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 공고 등록 테스트")
    public void createVolunteer_Bad_Request_Wrong_Input() throws Exception {
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
                .createdDate(LocalDate.of(2020, 5, 10))
                .build();

        mockMvc.perform(post("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(volunteer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비어 있는 값 입력")
    public void createVolunteer_Bad_Request_Empty_Input() throws Exception {
        VolunteerDto volunteerDto = VolunteerDto.builder().build();

        mockMvc.perform(post("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(volunteerDto)))
                .andExpect(status().isBadRequest());
    }

    private VolunteerDto givenVolunteerDto() {
        return VolunteerDto.builder()
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
                .build();
    }
}