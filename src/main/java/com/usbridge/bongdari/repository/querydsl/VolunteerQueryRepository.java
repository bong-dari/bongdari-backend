package com.usbridge.bongdari.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.usbridge.bongdari.model.Volunteer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.usbridge.bongdari.model.QVolunteer.volunteer;

@RequiredArgsConstructor
@Repository
public class VolunteerQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Volunteer> getVolunteersBySearch(LocalDate date, Integer capacity, String city, String gu, String searchKeyword, Pageable page) {
        QueryResults<Volunteer> volunteers = jpaQueryFactory
                .selectFrom(volunteer)
                .where(eqCity(city), eqGu(gu), includeDate(date), goeCapacity(capacity), containsSearchKeywordAtTitle(searchKeyword))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetchResults();

        return new PageImpl<>(volunteers.getResults(), page, volunteers.getTotal());
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
        return volunteer.startDate.loe(date).and(volunteer.endDate.goe(date));
    }

    private BooleanExpression goeCapacity(Integer capacity) {
        if (capacity == null) {
            return null;
        }
        return volunteer.capacity.goe(capacity);
    }

    private BooleanExpression containsSearchKeywordAtTitle(String searchKeyword) {
        if (searchKeyword == null) {
            return null;
        }
        return volunteer.title.contains(searchKeyword);
    }
}
