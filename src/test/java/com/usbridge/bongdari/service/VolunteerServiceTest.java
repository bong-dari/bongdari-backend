package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.exception.BadRequestException;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.repository.VolunteerRepository;
import com.usbridge.bongdari.repository.querydsl.VolunteerQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @InjectMocks
    private VolunteerService volunteerService;

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private VolunteerQueryRepository volunteerQueryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("봉사공고 등록")
    public void createVolunteer() {
        when(volunteerRepository.save(mockVolunteer(50L))).thenReturn(mockVolunteer(50L));
        when(modelMapper.map(mockVolunteerDto(), Volunteer.class)).thenReturn(mockVolunteer(50L));

        Volunteer volunteer = volunteerService.createVolunteer(mockVolunteerDto());

        assertThat(volunteer.getId()).isEqualTo(50);
    }

    @Test
    @DisplayName("봉사공고 상세보기")
    public void findVolunteer() {
        when(volunteerRepository.findById(50L)).thenReturn(Optional.of(mockVolunteer(50L)));

        Volunteer volunteer = volunteerService.findVolunteerById(50L);

        assertThat(volunteer.getTitle()).isEqualTo("봉사 활동");
        assertThat(volunteer.getDetails()).isEqualTo("봉사하기");
        assertThat(volunteer.getTime()).isEqualTo("오전10시부터");
    }

    @Test
    @DisplayName("존재하지 않는 봉사공고 조회")
    public void findVolunteer_Not_Exist_Volunteer() {
        when(volunteerRepository.findById(1000L)).thenThrow(new RuntimeException("존재하지 않는 봉사활동 공고입니다."));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> volunteerService.findVolunteerById(1000L));

        assertThat(ex.getMessage()).isEqualTo("존재하지 않는 봉사활동 공고입니다.");
    }

    @Test
    @DisplayName("봉사공고 삭제")
    public void deleteVolunteer() throws BadRequestException {
        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(mockVolunteer(1L)));

        Volunteer volunteer = volunteerService.deleteVolunteerById(1L);

        verify(volunteerRepository, times(1)).delete(volunteer);
        assertThat(volunteer).isNotNull();
    }

    @Test
    @DisplayName("없는 봉사공고 삭제 404")
    public void deleteVolunteer_Not_Exist() throws BadRequestException {
        when(volunteerRepository.findById(6545423L)).thenThrow(new BadRequestException("존재하지 않는 봉사활동 공고입니다."));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> volunteerService.deleteVolunteerById(6545423L));

        assertThat(ex.getMessage()).isEqualTo("존재하지 않는 봉사활동 공고입니다.");
    }

    @Test
    @DisplayName("봉사공고 검색 (기본)")
    public void findVolunteers() {
        when(volunteerQueryRepository.getVolunteersBySearch(null, null, null, null, null, PageRequest.of(0, 20)))
                .thenReturn(mockVolunteers());

        Page<Volunteer> volunteerSearchDtos = volunteerService.findVolunteersBySearch(null, null, null, null, null, PageRequest.of(0, 20));

        assertThat(volunteerSearchDtos.getTotalElements()).isEqualTo(10);
    }

    private Volunteer mockVolunteer(long id) {
        return Volunteer.builder()
                .id(id)
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
                .deadline(LocalDate.of(2020, 4, 20))
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

    private Page<Volunteer> mockVolunteers() {
        List<Volunteer> volunteers = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            volunteers.add(mockVolunteer(i));
        }

        return new PageImpl<>(volunteers, PageRequest.of(0, 20), volunteers.size());
    }
}
