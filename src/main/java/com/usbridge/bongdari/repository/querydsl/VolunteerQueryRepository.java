package com.usbridge.bongdari.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.usbridge.bongdari.model.Volunteer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.usbridge.bongdari.model.QVolunteer.volunteer;

@RequiredArgsConstructor
@Repository
public class VolunteerQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Volunteer> getVolunteersBySearch(LocalDate startDate, LocalDate endDate, Integer capacity, String city, String gu, String searchKeyword) {
        return null;
    }

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

    private BooleanExpression includeDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return volunteer.startDate.goe(date).and(volunteer.endDate.lt(date));
    }
}
