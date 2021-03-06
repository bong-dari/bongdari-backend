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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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
    @DisplayName("??????????????? ?????? ??????")
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
    @DisplayName("?????? ?????? ??? ?????? ?????? ????????? ????????? ????????? ???????????? ?????? ?????? ?????????")
    public void createVolunteer_Bad_Request_Wrong_Input() throws Exception {
        Volunteer volunteer = Volunteer.builder()
                .id(50L)
                .title("?????? ??????")
                .details("????????????")
                .time("??????10?????????")
                .contact("010-1234-5678")
                .capacity(10)
                .gender(Gender.ALL)
                .city("???????????????")
                .gu("?????????")
                .location("??????????????????")
                .manager("?????????")
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
    @DisplayName("?????? ?????? ??? ??????")
    public void createVolunteer_Bad_Request_Empty_Input() throws Exception {
        VolunteerDto volunteerDto = VolunteerDto.builder().build();

        mockMvc.perform(post("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(volunteerDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("???????????? ????????????")
    public void findVolunteer() throws Exception {
        mockMvc.perform(get("/api/volunteer/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("gender").exists())
                .andExpect(jsonPath("time").exists())
                .andExpect(jsonPath("capacity").exists())
                .andExpect(jsonPath("location").exists());
    }

    @Test
    @DisplayName("?????? ???????????? ???????????? 404")
    public void findVolunteer_404() throws Exception {
        mockMvc.perform(get("/api/volunteer/16234")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("???????????? ????????????")
    public void deleteVolunteer() throws Exception {
        mockMvc.perform(delete("/api/volunteer/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ???????????? 400")
    public void deleteVolunteer_Not_Exist_400() throws Exception {
        mockMvc.perform(delete("/api/volunteer/15674")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("???????????? ?????? (???)")
    public void findVolunteers_city() throws Exception {
        mockMvc.perform(get("/api/volunteers?city=???????????????")
                .content(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].city").value("???????????????"));
    }

    @Test
    @DisplayName("???????????? ?????? (???, ???)")
    public void findVolunteers_city_gu() throws Exception {
        mockMvc.perform(get("/api/volunteers?city=???????????????&gu=?????????")
                .content(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].city").value("???????????????"))
                .andExpect(jsonPath("$.content[0].gu").value("?????????"));
    }

    @Test
    @DisplayName("???????????? ??????")
    public void modifyVolunteer() throws Exception {
        mockMvc.perform(put("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(VolunteerDtoWithId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("?????????"))
                .andExpect(jsonPath("$.gu").value("?????????"))
                .andExpect(jsonPath("$.contact").value("010-0000-0000"))
                .andExpect(jsonPath("$.capacity").value(10));
    }

    @Test
    @DisplayName("???????????? ?????? (id null)")
    public void modifyVolunteer_id_null() throws Exception {
        mockMvc.perform(put("/api/volunteer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(givenVolunteerDto())))
                .andExpect(status().isBadRequest());
    }

    private VolunteerDto givenVolunteerDto() {
        return VolunteerDto.builder()
                .title("?????? ??????")
                .details("????????????")
                .time("??????10?????????")
                .contact("010-1234-5678")
                .capacity(10)
                .gender(Gender.ALL)
                .city("???????????????")
                .gu("?????????")
                .location("??????????????????")
                .manager("?????????")
                .startDate(LocalDate.of(2050, 5, 1))
                .endDate(LocalDate.of(2020, 5, 10))
                .build();
    }

    private VolunteerDto VolunteerDtoWithId() {
        return VolunteerDto.builder()
                .id(1L)
                .title("?????? ??????")
                .details("????????????")
                .time("??????10?????????")
                .contact("010-0000-0000")
                .capacity(10)
                .gender(Gender.ALL)
                .city("?????????")
                .gu("?????????")
                .location("??????????????????")
                .manager("?????????")
                .startDate(LocalDate.of(2050, 5, 1))
                .endDate(LocalDate.of(2020, 5, 10))
                .build();
    }
}