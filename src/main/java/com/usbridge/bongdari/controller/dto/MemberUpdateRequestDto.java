package com.usbridge.bongdari.controller.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String nickname;
    private String mobile;
    private Boolean smsAgreement;
}
