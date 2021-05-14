package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.SNS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember() {
        memberRepository.save(Member.builder()
                .id(null)
                .nickname("jwljwl")
                .name("우정임")
                .smsAgreement(true)
                .birthDate(LocalDate.of(1991,03,01))
                .contact("01066075331")
                .createdDate(LocalDate.now())
                .email("ljwljw@gmail.com")
                .gender(Gender.MEN)
                .isDeleted(false)
                .sns(SNS.KAKAO)
                .build());
    }

}
