package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/{board_id}")
    public BoardResponseDto getBoard(@PathVariable Long board_id){
        return boardService.getBoard(board_id);
    }

}