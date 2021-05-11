package com.usbridge.bongdari.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private String nickname;
    private String contact;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private LocalDate createdDate;
    private String city;
    private String gu;
}
