package com.usbridge.bongdari.config.auth.dto;

import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.SNS;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String nickname;
    private String email;
    private String mobile;
    private Boolean smsAgreement;
    private LocalDate birthDate;
    private Gender gender;
    private SNS sns;

    public SessionMember(Member member){
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.mobile = member.getMobile();
        this.smsAgreement = member.getSmsAgreement();
        this.birthDate = member.getBirthDate();
        this.gender = member.getGender();
        this.sns = member.getSns();
    }
}
