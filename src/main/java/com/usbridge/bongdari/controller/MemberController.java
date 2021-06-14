package com.usbridge.bongdari.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    @PostMapping("/member/agreement")
    public ResponseEntity<?> updateMember(){

        return ResponseEntity.ok(null);
    }
}
