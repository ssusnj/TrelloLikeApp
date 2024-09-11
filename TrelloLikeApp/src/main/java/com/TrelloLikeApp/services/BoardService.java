package com.TrelloLikeApp.services;

import com.TrelloLikeApp.dtos.BoardDto;
import com.TrelloLikeApp.entity.Board;
import com.TrelloLikeApp.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ListService listService;

    public BoardDto createBoard(String name, String description) {
        var board = new Board();
        board.setName(name);
        board.setDescription(description);
        var createdBoard = boardRepository.save(board);
        return convertToDto(createdBoard);
    }

    public BoardDto deleteBoard(Long boardId) {
        var boardToDelete = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        listService.deleteListsFromBoard(boardId);
        boardRepository.delete(boardToDelete);
        return convertToDto(boardToDelete);
    }

    public Set<BoardDto> getAllBoards() {
        var boards = boardRepository.findAll();
        return boards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    public BoardDto getBoardById(Long id) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found."));
        return convertToDto(board);
    }

    private BoardDto convertToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .lists(listService.getListsFromBoard(board.getId()))
                .build();
    }
}