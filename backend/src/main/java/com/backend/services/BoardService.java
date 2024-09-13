package com.backend.services;

import com.backend.dtos.BoardDto;
import com.backend.entity.Board;
import com.backend.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ListService listService;
    private final UserService userService;

    public BoardDto createBoard(String username, BoardDto boardDto) {
        var board = new Board();
        board.setName(boardDto.getName());
        board.setDescription(boardDto.getDescription());
        board.setCreatedBy(userService.getUserByUsername(username));
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

    public Set<BoardDto> getAllBoardsForUser(Long userId) {
        var boards = boardRepository.findAllByCreatedBy_Id(userId);
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
                .createdBy(userService.getUserById(board.getCreatedBy().getId()))
                .lists(listService.getListsFromBoard(board.getId()))
                .build();
    }
}