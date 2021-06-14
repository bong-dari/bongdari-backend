package com.usbridge.bongdari.model;

import com.usbridge.bongdari.model.enums.Category;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacity;

    private String contact;

    private LocalDate startDate;

    private LocalDate endDate;

    private String details;

    private String city;

    private String gu;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
