package com.usbridge.bongdari.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VolunteerSearchDto {
    private long id;

    private String title;

    private String city;

    private String gu;

    private Integer capacity;

    private LocalDate startDate;

    private LocalDate endDate;
}
