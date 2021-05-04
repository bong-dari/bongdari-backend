package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
