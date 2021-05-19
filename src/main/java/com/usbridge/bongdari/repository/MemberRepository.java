package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
