package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.exception.ResourceNotFoundException;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.repository.BoardRepository;
import com.usbridge.bongdari.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public BoardResponseDto findById(Long id){

        Board board = boardRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("해당 게시글이 없습니다. id = " + id));
        Member member = memberRepository.findById(board.getId()).orElseThrow(() ->
                new ResourceNotFoundException("회원을 찾을 수 없습니다. id = " + board.getId()));

        return BoardResponseDto.builder()
                .nickname(member.getNickname())
                .contact(board.getContact())
                .startDate(board.getStartDate())
                .endDate(board.getEndDate())
                .createdDate(board.getCreatedDate())
                .capacity(board.getCapacity())
                .city(board.getCity())
                .gu(board.getGu())
                .build();
    }
}
