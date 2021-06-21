package com.usbridge.bongdari.model;

import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.Role;
import com.usbridge.bongdari.model.enums.SNS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    private Boolean smsAgreement;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SNS sns;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member loginUpdate(String name, String email, LocalDate birthDate, Gender gender){
        this.name = name;
        this.email = email;
//        this.mobile = mobile;
        this.birthDate = birthDate;
        this.gender = gender;
        return this;
    }

    public Member update(String nickname, String mobile, Boolean smsAgreement){
        this.nickname = nickname != null ? nickname : this.nickname;
        this.mobile = mobile != null ? mobile : this.mobile;
        this.smsAgreement = smsAgreement != null ? smsAgreement : this.smsAgreement;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
