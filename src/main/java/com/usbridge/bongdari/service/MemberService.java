package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.MemberDto;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member updateMember(Long id, MemberDto requestDto){
        Member member = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 사용자가 없습니다. id = " + id));

        member.update(requestDto.getNickname(), requestDto.getMobile(), requestDto.getSmsAgreement());

        return memberRepository.save(member);
    }
}
