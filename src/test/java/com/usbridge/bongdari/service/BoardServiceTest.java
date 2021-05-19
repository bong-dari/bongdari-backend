package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Category;
import com.usbridge.bongdari.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @DisplayName("게시판 리스트 조회")
    @Test
    void findByCategoryAndAddress() {
        when(boardRepository.findByCategoryAndCityAndGu(Category.TOWN, "서울시", "강동구", PageRequest.of(0, 2))).thenReturn(givenBoardPage());

        Page<BoardResponseDto> boardPage = boardService.findByCategoryAndAddress(2, "서울시", "강동구", PageRequest.of(0, 2));

        assertThat(boardPage.toList().get(0).getCategory()).isEqualTo(Category.TOWN);
        assertThat(boardPage.toList().get(0).getCity()).isEqualTo("서울시");
        assertThat(boardPage.toList().get(0).getGu()).isEqualTo("강동구");
        assertThat(boardPage.toList().get(1).getCategory()).isEqualTo(Category.TOWN);
        assertThat(boardPage.toList().get(1).getCity()).isEqualTo("서울시");
        assertThat(boardPage.toList().get(1).getGu()).isEqualTo("강동구");
    }

    // given 데이터 따로 만들어야 할지
    @DisplayName("위치정보 x 게시판 리스트 조회")
    @Test
    void findByCategoryAndCity() {
        when(boardRepository.findByCategoryAndCity(Category.TOWN, "서울시", PageRequest.of(0, 2))).thenReturn(givenBoardPage());

        Page<BoardResponseDto> boardPage = boardService.findByCategoryAndAddress(2, "서울시", null, PageRequest.of(0, 2));

        assertThat(boardPage.toList().get(0).getCategory()).isEqualTo(Category.TOWN);
        assertThat(boardPage.toList().get(0).getCity()).isEqualTo("서울시");
        assertThat(boardPage.toList().get(1).getCategory()).isEqualTo(Category.TOWN);
        assertThat(boardPage.toList().get(1).getCity()).isEqualTo("서울시");
    }

    private List<Board> givenBoardList() {
        List<Board> boardList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            boardList.add(Board.builder()
                    .id((long)i)
                    .category(Category.TOWN)
                    .contact("")
                    .capacity(3)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .createdDate(LocalDateTime.now())
                    .details("")
                    .city("서울시")
                    .gu("강동구")
                    .member(Member.builder().nickname("").build())
                    .build());
        }
        return boardList;
    }

    private Page<Board> givenBoardPage() {
        return new PageImpl<>(givenBoardList());
    }
}