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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        when(volunteerService.findVolunteerById(100L)).thenReturn(null);

        Volunteer volunteer = volunteerService.findVolunteerById(100L);

        assertThat(volunteer).isNull();
    }

    @Test
    @DisplayName("봉사공고 삭제")
    public void deleteVolunteer() {
        when(volunteerService.deleteVolunteerById(1L)).thenReturn(mockVolunteer());

        Volunteer volunteer = volunteerService.deleteVolunteerById(1L);

        verify(volunteerService, times(1)).deleteVolunteerById(1L);
        assertThat(volunteer).isNotNull();
    }

    @Test
    @DisplayName("없는 봉사공고 삭제 404")
    public void deleteVolunteer_Not_Exist() {
        when(volunteerService.deleteVolunteerById(6545423L)).thenReturn(null);

        Volunteer volunteer = volunteerService.deleteVolunteerById(6545423L);

        verify(volunteerService, times(1)).deleteVolunteerById(6545423L);
        assertThat(volunteer).isNull();
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