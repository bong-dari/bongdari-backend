package com.usbridge.bongdari.model;

import com.usbridge.bongdari.model.enums.Gender;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String details;

    private String location;

    private String time;

    private String manager;

    private String contact;

    private Integer capacity;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate createdDate;

    private Gender gender;
}
