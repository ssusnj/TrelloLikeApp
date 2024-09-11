package com.TrelloLikeApp.controllers;

import com.TrelloLikeApp.dtos.BoardDto;
import com.TrelloLikeApp.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<Set<BoardDto>> getAllBoards() { // todo: check @PreAuthorize("hasRole('ADMIN')")
        var boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long boardId) {
        var board = boardService.getBoardById(boardId);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto board) {
        var createdBoard = boardService.createBoard(board.getName(), board.getDescription());
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardDto> deleteBoard(@PathVariable Long boardId) {
        var deleteBoard = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(deleteBoard, HttpStatus.OK);
    }

}