package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("카테고리, 도시별 게시글 검색")
    @Test
    void findByCategoryAndCity(){
        Category category = Category.TOWN;
        String city = "서울시";

        Pageable pageable = PageRequest.of(0, 5);
        Board board = boardRepository.findByCategoryAndCity(category, city, pageable).toList().get(0);

        assertThat(board.getCategory()).isEqualTo(Category.TOWN);
        assertThat(board.getCity()).isEqualTo(city);
    }

    @DisplayName("카테고리, 도시, 구별 게시글 검색")
    @Test
    void findByCategoryAndCityAndGu(){
        Category category = Category.TOWN;
        String city = "서울시";
        String gu = "강동구";

        Pageable pageable = PageRequest.of(0, 5);
        Board board = boardRepository.findByCategoryAndCityAndGu(category, city, gu, pageable).toList().get(0);

        assertThat(board.getCategory()).isEqualTo(Category.TOWN);
        assertThat(board.getCity()).isEqualTo(city);
        assertThat(board.getGu()).isEqualTo(gu);
    }
}
