package com.usbridge.bongdari.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VolunteerController {

    @GetMapping("/volunteer/{id}")
    public void getVolunteer(@PathVariable Long id) {
    }
}
