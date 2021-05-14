package com.usbridge.bongdari.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private String nickname;
    private String contact;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createdDate;
    private int capacity;
    private String city;
    private String gu;
}
