package com.usbridge.bongdari.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.usbridge.bongdari.model.QVolunteer.volunteer;

@RequiredArgsConstructor
@Repository
public class VolunteerQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression eqCity(String city) {
        if (city == null) {
            return null;
        }
        return volunteer.city.eq(city);
    }

    private BooleanExpression eqGu(String gu) {
        if (gu == null) {
            return null;
        }
        return volunteer.gu.eq(gu);
    }
}
