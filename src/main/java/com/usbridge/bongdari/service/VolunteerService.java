package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.controller.dto.VolunteerSearchDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.repository.VolunteerRepository;
import com.usbridge.bongdari.repository.querydsl.VolunteerQueryRepository;
import exception.BadRequestException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public Volunteer findVolunteerById(Long id) throws NotFoundException {
        return volunteerRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 봉사활동 공고입니다."));
    }

    public Volunteer deleteVolunteerById(Long id) throws BadRequestException {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new BadRequestException("존재하지 않는 봉사활동 공고입니다."));

        volunteerRepository.delete(volunteer);

        return volunteer;
    }

    public Page<VolunteerSearchDto> findVolunteersBySearch(LocalDate date, Integer capacity, String city, String gu, String searchKeyword, Pageable page) {

        Page<Volunteer> volunteers = volunteerQueryRepository.getVolunteersBySearch(date, capacity, city, gu, searchKeyword, page);

        List<VolunteerSearchDto> volunteerSearchDtos = volunteers
                .stream()
                .map(volunteer -> modelMapper.map(volunteer, VolunteerSearchDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(volunteerSearchDtos, page, volunteers.getTotalElements());
    }
}
