package com.usbridge.bongdari.service;

import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.enums.Category;
import com.usbridge.bongdari.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardResponseDto> findByCategory(int category_id, String city, String gu, Pageable pageable){
        Page<Board> boardPage = gu == null ?
                boardRepository.findByCategoryAndCity(Category.values()[category_id], city, pageable) :
                boardRepository.findByCategoryAndCityAndGu(Category.values()[category_id], city, gu, pageable);

        return boardPageToBoardResponseDtoPage(boardPage, pageable);
    }

//    @Transactional
//    public Long saveBoard(BoardSaveRequestDto requestDto){
////        return boardRepository.save(requestDto.toEntity()).getId();
//        return null;
//    }

    private Page<BoardResponseDto> boardPageToBoardResponseDtoPage(Page<Board> boardPage, Pageable pageable){
        List<Board> boardList = boardPage.toList();
        List<BoardResponseDto> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            dtoList.add(boardToBoardResponseDto(board));
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtoList.size());
        return new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());
    }

    private BoardResponseDto boardToBoardResponseDto(Board board){
        return BoardResponseDto.builder()
                .nickname(board.getMember().getNickname())
                .contact(board.getContact())
                .category(board.getCategory())
                .details(board.getDetails())
                .startDate(board.getStartDate())
                .endDate(board.getEndDate())
                .createdDate(board.getCreatedDate())
                .capacity(board.getCapacity())
                .city(board.getCity())
                .gu(board.getGu())
                .build();
    }
}
