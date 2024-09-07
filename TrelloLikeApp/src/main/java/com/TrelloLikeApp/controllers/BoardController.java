package com.TrelloLikeApp.controllers;

import com.TrelloLikeApp.dtos.BoardDto;
import com.TrelloLikeApp.entity.Board;
import com.TrelloLikeApp.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        var boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long id) {
        var board = boardService.getBoardById(id);
//        if (board == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } // cannot return null, should it instead of exception? try-catch?
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BoardDto>> getBoardsByUser(@PathVariable Long userId) {
        var boards = boardService.getByUserId(userId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PostMapping // ("/{userId})
    public ResponseEntity<BoardDto> createBoard(@RequestBody Board board) {
        var createdBoard = boardService.createBoard(board.getName(), board.getDescription(), board.getCreatedBy().getId());
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

}