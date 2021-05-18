package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.BoardResponseDto;
import com.usbridge.bongdari.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/{category_id}")
    public Page<BoardResponseDto> getBoardList(@PathVariable int category_id,
                                               @RequestParam(defaultValue = "서울시", required = false) String city,
                                               @RequestParam(required = false) String gu, @PageableDefault(size = 10) Pageable pageable){
        return boardService.findByCategoryAndAddress(category_id, city, gu, pageable);
    }
}