package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.VolunteerDto;
import com.usbridge.bongdari.controller.dto.VolunteerSearchDto;
import com.usbridge.bongdari.exception.BadRequestException;
import com.usbridge.bongdari.model.Volunteer;
import com.usbridge.bongdari.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<VolunteerDto> getVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.findVolunteerById(id);

        return ResponseEntity.ok(modelMapper.map(volunteer, VolunteerDto.class));
    }

    @GetMapping("/volunteers")
    public ResponseEntity<Page<VolunteerSearchDto>> getVolunteersBySearch(@RequestParam(required = false) LocalDate date,
                                                                          @RequestParam(required = false) Integer capacity,
                                                                          @RequestParam(required = false) String city,
                                                                          @RequestParam(required = false) String gu,
                                                                          @RequestParam(required = false) String searchKeyword,
                                                                          @PageableDefault(size = 20) Pageable page) {

        Page<Volunteer> volunteers = volunteerService.findVolunteersBySearch(date, capacity, city, gu, searchKeyword, page);

        List<VolunteerSearchDto> volunteerSearchDtos = volunteers
                .stream()
                .map(volunteer -> modelMapper.map(volunteer, VolunteerSearchDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(volunteerSearchDtos, page, volunteers.getTotalElements()));
    }

    @DeleteMapping("/volunteer/{id}")
    public ResponseEntity<VolunteerDto> deleteVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.deleteVolunteerById(id);

        return ResponseEntity.ok(modelMapper.map(volunteer, VolunteerDto.class));
    }

    @PutMapping("/volunteer")
    public ResponseEntity<VolunteerDto> putVolunteer(@RequestBody @Valid VolunteerDto volunteerDto) {

        Volunteer volunteer = volunteerService.modifyVolunteer(volunteerDto);

        return ResponseEntity.ok(modelMapper.map(volunteer, VolunteerDto.class));
    }
}