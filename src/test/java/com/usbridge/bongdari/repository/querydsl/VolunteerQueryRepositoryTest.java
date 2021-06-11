package com.usbridge.bongdari.repository.querydsl;

import com.usbridge.bongdari.model.Volunteer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VolunteerQueryRepositoryTest {

    @Autowired
    private VolunteerQueryRepository volunteerQueryRepository;

    @Test
    @DisplayName("봉사활동 공고 가져오기 (기본)")
    public void getVolunteersBySearch() {
        Page<Volunteer> volunteers = volunteerQueryRepository.getVolunteersBySearch(null, null, null,
                null, null, PageRequest.of(0, 20));

        assertThat(volunteers.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("봉사활동 공고 가져오기 (날짜)")
    public void getVolunteersBySearch_Date() {
        Page<Volunteer> volunteers = volunteerQueryRepository.getVolunteersBySearch(LocalDate.of(2021, 5, 11), null, null,
                null, null, PageRequest.of(0, 20));

        assertThat(volunteers.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("봉사활동 공고 가져오기 (인원)")
    public void getVolunteersBySearch_Capacity() {
        Page<Volunteer> volunteers = volunteerQueryRepository.getVolunteersBySearch(null, 4, null,
                null, null, PageRequest.of(0, 20));

        assertThat(volunteers.getTotalElements()).isEqualTo(3);
    }
}