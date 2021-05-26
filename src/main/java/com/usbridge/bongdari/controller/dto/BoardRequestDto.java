package com.usbridge.bongdari.controller.dto;

import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private Long id;
    private int capacity;
    private String contact;
    private LocalDate startDate;
    private LocalDate endDate;
    private String details;
    private String city;
    private String gu;
    private Category category;

    public Board toEntity(Member member) {
        return Board.builder()
                .capacity(capacity)
                .contact(contact)
                .startDate(startDate)
                .endDate(endDate)
                .createdDate(LocalDateTime.now().withNano(0))
                .details(details)
                .city(city)
                .gu(gu)
                .category(category)
                .member(member)
                .build();
    }
}
