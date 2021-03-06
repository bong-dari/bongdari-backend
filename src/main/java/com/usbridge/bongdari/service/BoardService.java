package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.BoardRequestDto;
import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Category;
import com.usbridge.bongdari.repository.BoardRepository;
import com.usbridge.bongdari.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public Page<BoardResponseDto> findByCategoryAndAddress(Category category, String city, String gu, Pageable pageable){
        // 나중에 QueryDSL로 변경해야함
        Page<Board> boardPage = gu == null ?
                boardRepository.findByCategoryAndCity(category, city, pageable) :
                boardRepository.findByCategoryAndCityAndGu(category, city, gu, pageable);
        return boardPageToBoardResponseDtoPage(boardPage, pageable);
    }

    @Transactional
    public Board createBoard(Long exToken, BoardRequestDto dto){
        // 인증 기능 완성되면 토큰받아 멤버 불러오는 코드 추가 예정
        Member member = memberRepository.findById(exToken).orElseThrow(() -> new ResourceNotFoundException("해당 id의 회원정보가 존재하지 않습니다."));
        return boardRepository.save(dto.toEntity(member));
    }

    @Transactional
    public Board deleteBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        boardRepository.delete(board);
        return board;
    }

    @Transactional
    public Board updateBoard(BoardRequestDto boardRequestDto){
        Board board = boardRepository.findById(boardRequestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        setBoard(board, boardRequestDto);
        return boardRepository.save(board);
    }

    private Page<BoardResponseDto> boardPageToBoardResponseDtoPage(Page<Board> boardPage, Pageable pageable){
        List<Board> boardList = boardPage.toList();
        List<BoardResponseDto> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            dtoList.add(new BoardResponseDto(board));
        }

        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    static void setBoard(Board board, BoardRequestDto boardRequestDto){
        board.setCapacity(boardRequestDto.getCapacity());
        board.setContact(boardRequestDto.getContact());
        board.setStartDate(boardRequestDto.getStartDate());
        board.setEndDate(boardRequestDto.getEndDate());
        board.setDetails(boardRequestDto.getDetails());
        board.setCity(boardRequestDto.getCity());
        board.setGu(boardRequestDto.getGu());
        board.setCategory(boardRequestDto.getCategory());
    }
}
