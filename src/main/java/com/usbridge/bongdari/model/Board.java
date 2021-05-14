package com.usbridge.bongdari.model;

import com.usbridge.bongdari.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacity;

    private String contact;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate createdDate;

    private String details;

    private String city;

    private String gu;

    private Category category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
