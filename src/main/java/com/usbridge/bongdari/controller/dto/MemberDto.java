package com.usbridge.bongdari.controller.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String nickname;
    private String mobile;
    private Boolean smsAgreement;
}
