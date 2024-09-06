package com.TrelloLikeApp.services;

import com.TrelloLikeApp.dtos.BoardDto;
import com.TrelloLikeApp.entity.Board;
import com.TrelloLikeApp.repositories.BoardRepository;
import com.TrelloLikeApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardDto createBoard(String name, String description, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); // not possible if path variable?

        var board = new Board();
        board.setName(name);
        board.setDescription(description);
        board.setCreatedBy(user);

        var createdBoard = boardRepository.save(board);
        return convertToDto(createdBoard);
    }

    public List<BoardDto> getAllBoards() {
        var boards = boardRepository.findAll();
        return boards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BoardDto getBoardById(Long id) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found."));
        return convertToDto(board);
    }

    public List<BoardDto> getByUserId(Long userId) {
        // todo: check user
        var boards = boardRepository.findAllByCreatedBy_Id(userId);
        return boards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BoardDto convertToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .createdBy(board.getCreatedBy().getUsername()) // todo: check if board should have setters
                .build();
    }

}