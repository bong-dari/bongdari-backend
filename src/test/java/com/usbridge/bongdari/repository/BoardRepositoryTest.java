package com.usbridge.bongdari.repository;

import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanUp(){
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("게시판저장_불러오기")
    void saveBoardLoad() {
        int capacity = 3;
        Category category = Category.TOWN;
        String contact = "01066075331";
        String details = "봉사활동 게시판 테스트 입니다.";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2021, 5, 16);

        Member member = memberRepository.findById((long)1).orElseThrow(ResourceNotFoundException::new);

        boardRepository.save(Board.builder()
                .id(null)
                .capacity(capacity)
                .category(category)
                .contact(contact)
                .details(details)
                .startDate(startDate)
                .endDate(endDate)
                .member(member)
                .build());

        List<Board> boardList = boardRepository.findAll();

        Board board = boardList.get(0);
        assertThat(board.getCapacity()).isEqualTo(capacity);
        assertThat(board.getCategory()).isEqualTo(category);
        assertThat(board.getContact()).isEqualTo(contact);
        assertThat(board.getDetails()).isEqualTo(details);
        assertThat(board.getStartDate()).isEqualTo(startDate);
        assertThat(board.getEndDate()).isEqualTo(endDate);
    }
}