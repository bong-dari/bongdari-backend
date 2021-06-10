package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.SNS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndSns(String email, SNS sns);
}
