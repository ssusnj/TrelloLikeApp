package com.backend.services;

import com.backend.dtos.BoardDto;

import java.util.Set;

public interface BoardService {

    BoardDto createBoard(String username, BoardDto boardDto);
    BoardDto deleteBoard(Long boardId); // VOID!
    Set<BoardDto> getAllBoardsForUser(Long userId);
    BoardDto getBoardById(Long id);
}
