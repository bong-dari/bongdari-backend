package com.usbridge.bongdari.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    @PatchMapping("/member")
    public ResponseEntity<?> updateMember(){

        return ResponseEntity.ok(null);
    }
}
