package com.usbridge.bongdari.controller.dto;

import com.usbridge.bongdari.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class VolunteerDto {
    private String title;

    private String details;

    private String location;

    private String time;

    private String manager;

    private String contact;

    private Integer capacity;

    private LocalDate startDate;

    private LocalDate endDate;

    private Gender gender;
}
