package com.TrelloLikeApp.controllers;

import com.TrelloLikeApp.dtos.BoardDto;
import com.TrelloLikeApp.services.BoardService;
import com.TrelloLikeApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards/{username}")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Set<BoardDto>> getAllBoardsForUser(@PathVariable String username) {
        var user = userService.getUserByUsername(username);
        var boards = boardService.getAllBoardsForUser(user.getId());
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<BoardDto> createBoard(@PathVariable String username, @RequestBody BoardDto board) {
        var createdBoard = boardService.createBoard(username, board);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long boardId) {
       var board =  boardService.getBoardById(boardId);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    @Transactional
    public ResponseEntity<BoardDto> deleteBoard(@PathVariable Long boardId) {
        var deleteBoard = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(deleteBoard, HttpStatus.OK);
    }

}