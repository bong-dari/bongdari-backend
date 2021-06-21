package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.MemberDto;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.Role;
import com.usbridge.bongdari.model.enums.SNS;
import com.usbridge.bongdari.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
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

    @DisplayName("회원 정보 수정 (존재하지 않는 회원)")
    @Test
    void updateMember_Not_Exist_Member(){
        // given
        Long givenMemberId = 100L;
        MemberDto dto = givenMemberDto();

        // when
        when(memberRepository.findById(givenMemberId)).thenThrow(new ResourceNotFoundException("해당 사용자가 없습니다. id = " + givenMemberId));

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.updateMember(givenMemberId, dto));

        // then
        assertThat(exception.getMessage()).isEqualTo("해당 사용자가 없습니다. id = " + givenMemberId);
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