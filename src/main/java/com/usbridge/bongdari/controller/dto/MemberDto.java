package com.usbridge.bongdari.controller.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class MemberUpdateDto {
    private String nickname;
    private String mobile;
    private Boolean smsAgreement;
}
