package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class VolunteerController {

    private final VolunteerRepository volunteerRepository;

    private final ModelMapper modelMapper;

    @PostMapping("/volunteer")
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody @Valid VolunteerDto volunteerDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Volunteer volunteer = modelMapper.map(volunteerDto, Volunteer.class);

        Volunteer newVolunteer = volunteerRepository.save(volunteer);

        return ResponseEntity.ok().body(newVolunteer);
    }
}