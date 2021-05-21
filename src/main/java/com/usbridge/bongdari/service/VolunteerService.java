package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.repository.VolunteerRepository;
import exception.BadRequestException;
import javassist.NotFoundException;
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

    public Volunteer findVolunteerById(Long id) throws NotFoundException {
        return volunteerRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 봉사활동 공고입니다."));
    }

    public Volunteer deleteVolunteerById(Long id) throws BadRequestException {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new BadRequestException("존재하지 않는 봉사활동 공고입니다."));

        volunteerRepository.delete(volunteer);

        return volunteer;
    }
}
