package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.model.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private VolunteerService volunteerService;

    @Test
    @DisplayName("봉사공고 등록")
    public void createVolunteer() {
        when(volunteerService.createVolunteer(givenVolunteerDto())).thenReturn(mockVolunteer());

        Volunteer volunteer = volunteerService.createVolunteer(givenVolunteerDto());

        assertThat(volunteer.getId()).isEqualTo(50);
    }

    private Volunteer mockVolunteer() {
        return Volunteer.builder()
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