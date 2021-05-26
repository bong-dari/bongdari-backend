package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByCategoryAndCity(Category category, String city, Pageable pageable);

    Page<Board> findByCategoryAndCityAndGu(Category category, String city, String gu, Pageable pageable);
}
