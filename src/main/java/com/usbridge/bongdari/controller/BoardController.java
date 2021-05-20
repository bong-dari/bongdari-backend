package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.BoardRequestDto;
import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.model.Board;
import com.usbridge.bongdari.model.enums.Category;
import com.usbridge.bongdari.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<Page<BoardResponseDto>> getBoardList(@RequestParam Category category,
                                       @RequestParam(defaultValue = "서울시", required = false) String city,
                                       @RequestParam(required = false) String gu, Pageable pageable){
        return ResponseEntity.ok(boardService.findByCategoryAndAddress(category, city, gu, pageable));
    }

    @PostMapping("/board")
    public ResponseEntity<Board> createBoard(@RequestBody BoardRequestDto boardRequestDto, Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(boardService.createBoard(1L, boardRequestDto));
    }
}