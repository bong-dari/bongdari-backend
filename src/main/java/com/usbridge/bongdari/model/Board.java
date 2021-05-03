package com.usbridge.bongdari.model;

import com.usbridge.bongdari.model.enums.Category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacity;

    private String contact;

    private LocalDate startDate;

    private LocalDate endDate;

    private String details;

    private Category category;
}
