package com.usbridge.bongdari.controller;

import com.usbridge.bongdari.controller.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    @GetMapping("/board/{board_id}")
    public BoardDto getBoard(@PathVariable Long board_id){
        return null;
    }

}