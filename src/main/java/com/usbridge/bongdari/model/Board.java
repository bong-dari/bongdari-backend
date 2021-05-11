package com.usbridge.bongdari.model;

import com.usbridge.bongdari.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @Column(name = "member_id")
    private Member member;
}
