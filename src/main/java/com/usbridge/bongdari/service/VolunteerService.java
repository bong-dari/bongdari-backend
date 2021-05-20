package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final ModelMapper modelMapper;

    public Volunteer createVolunteer(VolunteerDto volunteerDto) {
        Volunteer volunteer = modelMapper.map(volunteerDto, Volunteer.class);

        return volunteerRepository.save(volunteer);
    }

    public VolunteerDto findVolunteerById(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 봉사활동 공고입니다."));

        return modelMapper.map(volunteer, VolunteerDto.class);
    }

}
