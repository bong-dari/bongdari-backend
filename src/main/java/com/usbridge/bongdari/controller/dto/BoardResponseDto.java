package com.usbridge.bongdari.controller.dto;

import com.usbridge.bongdari.model.Board;
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
public class BoardResponseDto {
    private String nickname;
    private String contact;
    private Category category;
    private String details;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int capacity;
    private String city;
    private String gu;

    public BoardResponseDto(Board board) {
        this.nickname = board.getMember().getNickname();
        this.contact = board.getContact();
        this.category = board.getCategory();
        this.details = board.getDetails();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.createdDate = board.getCreatedDate().withNano(0);
        this.modifiedDate = board.getModifiedDate().withNano(0);
        this.capacity = board.getCapacity();
        this.city = board.getCity();
        this.gu = board.getGu();
    }
}
