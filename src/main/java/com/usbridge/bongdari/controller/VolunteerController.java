package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.model.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api")
public class VolunteerController {

    @PostMapping("/volunteer")
    public ResponseEntity createVolunteer() {

        URI createdUri = linkTo(Volunteer.class).slash("{id}").toUri();

        return ResponseEntity.created(createdUri).build();
    }
}
