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

@Transactional
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

    public Board createBoard(Long exToken, BoardRequestDto dto){
        // 인증 기능 완성되면 토큰받아 멤버 불러오는 코드 추가 예정
        Member member = memberRepository.findById(exToken).orElseThrow(() -> new ResourceNotFoundException("해당 id의 회원정보가 존재하지 않습니다."));
        return boardRepository.save(dto.toEntity(member));
    }

    public Board deleteBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 id의 회원정보가 존재하지 않습니다."));
        boardRepository.delete(board);
        return board;
    }

    private Page<BoardResponseDto> boardPageToBoardResponseDtoPage(Page<Board> boardPage, Pageable pageable){
        List<Board> boardList = boardPage.toList();
        List<BoardResponseDto> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            dtoList.add(new BoardResponseDto(board));
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtoList.size());
        return new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());
    }
}
