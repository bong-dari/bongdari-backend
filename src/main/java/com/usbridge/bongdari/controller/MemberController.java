package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.config.auth.LoginMember;
import com.usbridge.bongdari.config.auth.dto.SessionMember;
import com.usbridge.bongdari.controller.dto.MemberDto;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/member")
    public ResponseEntity<Member> updateMember(@LoginMember SessionMember member, @RequestBody MemberDto requestDto){
        return ResponseEntity.ok(memberService.updateMember(member.getId(), requestDto));
    }
}
