package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.MemberDto;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.Role;
import com.usbridge.bongdari.model.enums.SNS;
import com.usbridge.bongdari.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("회원 정보 수정")
    @Test
    void updateMember(){
        // given
        Member member = givenMember();
        MemberDto dto = givenMemberDto();

        // when
        when(memberRepository.findById(3L)).thenReturn(Optional.of(member));
        when(memberRepository.save(member)).thenReturn(member);

        member = memberService.updateMember(3L, dto);

        // then
        verify(memberRepository, times(1)).findById(3L);
        verify(memberRepository, times(1)).save(member);
        assertThat(member.getNickname()).isEqualTo(dto.getNickname());
        assertThat(member.getMobile()).isEqualTo(dto.getMobile());
        assertThat(member.getSmsAgreement()).isEqualTo(dto.getSmsAgreement());
    }

    private MemberDto givenMemberDto() {
        return MemberDto.builder()
                .nickname("MODIFIED")
                .mobile("01011112222")
                .smsAgreement(false)
                .build();
    }

    private Member givenMember() {
        return Member.builder()
                .id(3L)
                .name("임정우")
                .nickname("LNOAH")
                .email("limnoah0301@gmail.com")
                .mobile("01056781234")
                .smsAgreement(true)
                .isDeleted(false)
                .birthDate(LocalDate.of(2002, 12, 25))
                .gender(Gender.MALE)
                .sns(SNS.KAKAO)
                .role(Role.MEMBER)
                .build();
    }
}