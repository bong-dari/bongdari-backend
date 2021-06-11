package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.exception.BadRequestException;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.repository.VolunteerRepository;
import com.usbridge.bongdari.repository.querydsl.VolunteerQueryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerQueryRepository volunteerQueryRepository;

    private final VolunteerRepository volunteerRepository;

    private final ModelMapper modelMapper;

    public Volunteer createVolunteer(VolunteerDto volunteerDto) {
        Volunteer volunteer = modelMapper.map(volunteerDto, Volunteer.class);

        return volunteerRepository.save(volunteer);
    }

    public Volunteer findVolunteerById(Long id) throws ResourceNotFoundException {
        return volunteerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 봉사활동 공고입니다."));
    }

    public Volunteer deleteVolunteerById(Long id) throws BadRequestException {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new BadRequestException("존재하지 않는 봉사활동 공고입니다."));

        volunteerRepository.delete(volunteer);

        return volunteer;
    }

    public Page<Volunteer> findVolunteersBySearch(LocalDate date, Integer capacity, String city, String gu, String searchKeyword, Pageable page) {
        return volunteerQueryRepository.getVolunteersBySearch(date, capacity, city, gu, searchKeyword, page);
    }

    @Transactional
    public Volunteer modifyVolunteer(VolunteerDto volunteerDto) {
        if (volunteerDto.getId() == null) {
            throw new BadRequestException("봉사활동 공고 id 값이 null 입니다.");
        }

        Volunteer volunteer = volunteerRepository.findById(volunteerDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 봉사활동 공고입니다."));

        modelMapper.map(volunteerDto, volunteer);

        return volunteer;
    }
}
