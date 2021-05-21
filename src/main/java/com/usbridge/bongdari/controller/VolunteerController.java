package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.service.VolunteerService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class VolunteerController {

    private final VolunteerService volunteerService;

    private final ModelMapper modelMapper;

    @PostMapping("/volunteer")
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody @Valid VolunteerDto volunteerDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(volunteerService.createVolunteer(volunteerDto));
    }

    @GetMapping("/volunteer/{id}")
    public ResponseEntity<VolunteerDto> getVolunteer(@PathVariable Long id) throws NotFoundException {
        Volunteer volunteer = volunteerService.findVolunteerById(id);

        return ResponseEntity.ok(modelMapper.map(volunteer, VolunteerDto.class));
    }

    @DeleteMapping("/volunteer/{id}")
    public ResponseEntity<VolunteerDto> deleteVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.deleteVolunteerById(id);

        if (volunteer == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(modelMapper.map(volunteer, VolunteerDto.class));
    }
}