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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private VolunteerService volunteerService;

    @Test
    @DisplayName("봉사공고 등록")
    public void createVolunteer() {
        when(volunteerService.createVolunteer(mockVolunteerDto())).thenReturn(mockVolunteer());

        Volunteer volunteer = volunteerService.createVolunteer(mockVolunteerDto());

        assertThat(volunteer.getId()).isEqualTo(50);
    }

    @Test
    @DisplayName("봉사공고 상세보기")
    public void findVolunteer() {
        when(volunteerService.findVolunteerById(50L)).thenReturn(mockVolunteer());

        Volunteer volunteer = volunteerService.findVolunteerById(50L);

        assertThat(volunteer.getTitle()).isEqualTo("봉사 활동");
        assertThat(volunteer.getDetails()).isEqualTo("봉사하기");
        assertThat(volunteer.getTime()).isEqualTo("오전10시부터");
    }

    @Test
    @DisplayName("존재하지 않는 봉사공고 조회")
    public void findVolunteer_Not_Exist_Volunteer() {
        when(volunteerService.findVolunteerById(100L)).thenThrow(new RuntimeException("존재하지 않는 봉사활동 공고입니다."));

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> volunteerService.findVolunteerById(100L));

        assertThat(runtimeException.getMessage()).isEqualTo("존재하지 않는 봉사활동 공고입니다.");
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

    private VolunteerDto mockVolunteerDto() {
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