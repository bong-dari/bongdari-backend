package com.usbridge.bongdari.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VolunteerQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
