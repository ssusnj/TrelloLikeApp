package com.backend.services;

import com.backend.dtos.ListDto;
import com.backend.entity.List;
import com.backend.exceptions.AppException;
import com.backend.repositories.BoardRepository;
import com.backend.repositories.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;
    private final CardService cardService;

    public ListDto createList(String name, Long boardId) {
        var board = boardRepository.findById(boardId)
                .orElseThrow(() -> new AppException("Board not found.", HttpStatus.NOT_FOUND));
        var list = new List();
        list.setName(name);
        list.setBoard(board);
        var createdList = listRepository.save(list);
        return convertToDto(createdList);
    }

    public Set<ListDto> getListsFromBoard(Long boardId) {
        var lists =  listRepository.findAllByBoard_Id(boardId)
                .orElseThrow(() -> new AppException("Board not found.", HttpStatus.NOT_FOUND));
        return lists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    public void deleteListsFromBoard(Long boardId) {
        var boardLists = listRepository.findAllByBoard_Id(boardId)
                .orElseThrow(() -> new AppException("Lists not found.", HttpStatus.NOT_FOUND));
        boardLists.stream().map(List::getId).forEach(cardService::deleteCardsFromList);
        listRepository.deleteAll(boardLists);
    }

    public ListDto deleteList(Long listId) {
        var listToDelete = listRepository.findById(listId)
                .orElseThrow(() -> new AppException("List not found", HttpStatus.NOT_FOUND));
        cardService.deleteCardsFromList(listId);
        listRepository.delete(listToDelete);
        return convertToDto(listToDelete);
    }

    private ListDto convertToDto(List list) {
        return ListDto.builder()
                .id(list.getId())
                .name(list.getName())
                .cards(cardService.getCardsFromList(list.getId()))
                .build();
    }

}