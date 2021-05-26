package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.BoardRequestDto;
import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Category;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.SNS;
import com.usbridge.bongdari.repository.BoardRepository;
import com.usbridge.bongdari.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.usbridge.bongdari.service.BoardService.setBoard;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock private ModelMapper modelMapper;

    @DisplayName("게시글 리스트 조회")
    @Test
    void findByCategoryAndAddress() {
        when(boardRepository.findByCategoryAndCityAndGu(
                Category.TOWN
                , "서울시"
                , "강동구"
                ,PageRequest.of(0, 2))).thenReturn(givenBoardPage()
        );

        List<BoardResponseDto> boardPage =
                boardService.findByCategoryAndAddress(
                        Category.TOWN
                        , "서울시"
                        , "강동구"
                        , PageRequest.of(0, 2)
                ).toList();

        for (BoardResponseDto boardResponseDto : boardPage) {
            assertThat(boardResponseDto.getCategory()).isEqualTo(Category.TOWN);
            assertThat(boardResponseDto.getCity()).isEqualTo("서울시");
            assertThat(boardResponseDto.getGu()).isEqualTo("강동구");
        }
    }

    @DisplayName("게시글 리스트 조회(위치정보 x)")
    @Test
    void findByCategoryAndCity() {
        when(boardRepository.findByCategoryAndCity(Category.TOWN, "서울시", PageRequest.of(0, 2))).thenReturn(givenBoardPage());

        List<BoardResponseDto> boardPage =
                boardService.findByCategoryAndAddress(Category.TOWN, "서울시", null, PageRequest.of(0, 2)).toList();

        for(BoardResponseDto boardResponseDto : boardPage){
            assertThat(boardResponseDto.getCategory()).isEqualTo(Category.TOWN);
            assertThat(boardResponseDto.getCity()).isEqualTo("서울시");
        }
    }

    @DisplayName("게시글 등록")
    @Test
    void createBoard() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(givenMember()));
        when(boardRepository.save(givenBoardRequestDto().toEntity(givenMember()))).thenReturn(givenBoardRequestDto().toEntity(givenMember()));

        Board board = boardService.createBoard(1L, givenBoardRequestDto());

        verify(boardRepository, times(1)).save(givenBoardRequestDto().toEntity(givenMember()));
        assertThat(board.getMember().getId()).isEqualTo(1L);
        assertThat(board.getCapacity()).isEqualTo(11);
        assertThat(board.getCity()).isEqualTo("경기도");
        assertThat(board.getGu()).isEqualTo("화성시");
    }

    @DisplayName("게시글 삭제")
    @Test
    void deleteBoard() {
        when(boardRepository.findById(1L)).thenReturn(Optional.of(givenBoard()));

        Board board = boardService.deleteBoardById(1L);

        verify(boardRepository, times(1)).delete(board);
        assertThat(boardService.deleteBoardById(1L)).isNotNull();
    }

    @DisplayName("게시글 삭제 (게시글 없음)")
    @Test
    void deleteBoard_ResourceNotFound() {
        when(boardRepository.findById(1991L)).thenThrow(new ResourceNotFoundException("해당 id의 게시글이 존재하지 않습니다."));

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> boardService.deleteBoardById(1991L));

        assertThat(exception.getMessage()).isEqualTo("해당 id의 게시글이 존재하지 않습니다.");
    }

    @DisplayName("게시글 수정")
    @Test
    void updateBoard() {
        Board updateBoard = givenBoard();
        setBoard(updateBoard, givenUpdateBoard());

        when(boardRepository.findById(givenUpdateBoard().getId())).thenReturn(Optional.of(givenBoard()));
        when(boardRepository.save(any(Board.class))).thenReturn(updateBoard);

        Board board = boardService.updateBoard(givenUpdateBoard());

        verify(boardRepository, times(1)).save(updateBoard);
        assertThat(board.getCapacity()).isEqualTo(15);
        assertThat(board.getCity()).isEqualTo("제주도");
        assertThat(board.getGu()).isEqualTo("서귀포시");
    }

    @DisplayName("게시글 수정 (게시글 없음)")
    @Test
    void updateBoard_ResourceNotFound() {
        when(boardRepository.findById(1L)).thenThrow(new ResourceNotFoundException("해당 id의 게시글이 존재하지 않습니다."));

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> boardService.updateBoard(givenBoardRequestDto()));

        assertThat(exception.getMessage()).isEqualTo("해당 id의 게시글이 존재하지 않습니다.");
    }

    private BoardRequestDto givenUpdateBoard() {
        return BoardRequestDto.builder()
                .capacity(15)
                .city("제주도")
                .gu("서귀포시")
                .build();
    }

    private Board givenBoard(){
        return Board.builder()
                .id(1L)
                .capacity(3)
                .category(Category.TOWN)
                .contact("01011112222")
                .details("1. 게시판 테스트입니다")
                .startDate(LocalDate.of(2021, 5, 22))
                .endDate(LocalDate.of(2021, 6, 22))
                .city("서울시")
                .gu("강동구")
                .build();
    }

    private BoardRequestDto givenBoardRequestDto() {
        return BoardRequestDto.builder()
                .id(1L)
                .capacity(11)
                .category(Category.TOGETHER)
                .contact("01055556666")
                .details("게시판 수정 테스트 입니다")
                .startDate(LocalDate.of(2021, 5, 15))
                .endDate(LocalDate.of(2021, 5, 20))
                .city("경기도")
                .gu("화성시")
                .build();
    }

    private Member givenMember() {
        return Member.builder()
                .id(1L)
                .name("임정우")
                .nickname("jeongwoolim")
                .email("jwlimtest@gmail.com")
                .contact("01066075331")
                .smsAgreement(true)
                .isDeleted(false)
                .birthDate(LocalDate.of(1991, 3, 1))
                .createdDate(LocalDate.of(2021, 2, 15))
                .gender(Gender.MALE)
                .sns(SNS.KAKAO)
                .build();
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