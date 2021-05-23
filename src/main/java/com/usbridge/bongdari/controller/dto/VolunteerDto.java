package com.usbridge.bongdari.controller.dto;

import com.usbridge.bongdari.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String details;

    @NotEmpty
    private String location;

    @NotEmpty
    private String time;

    @NotEmpty
    private String manager;

    @NotEmpty
    private String contact;

    @Min(1)
    private Integer capacity;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Gender gender;
}
