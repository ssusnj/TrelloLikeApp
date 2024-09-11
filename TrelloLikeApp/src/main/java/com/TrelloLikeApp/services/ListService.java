package com.TrelloLikeApp.services;

import com.TrelloLikeApp.dtos.ListDto;
import com.TrelloLikeApp.entity.List;
import com.TrelloLikeApp.repositories.BoardRepository;
import com.TrelloLikeApp.repositories.ListRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("Board not found."));
        var list = List.builder()
                .name(name)
                .board(board)
                .build();
        var createdList = listRepository.save(list);
        return convertToDto(createdList);
    }

    public Set<ListDto> getListsFromBoard(Long boardId) {
        var lists =  listRepository.findAllByBoard_Id(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found."));
        return lists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    public void deleteListsFromBoard(Long boardId) {
        var boardLists = listRepository.findAllByBoard_Id(boardId)
                .orElseThrow(() -> new RuntimeException("Lists not found."));
        boardLists.stream().map(List::getId).forEach(cardService::deleteCardsFromList);
        listRepository.deleteAll(boardLists);
    }

    public void deleteList(Long listId) {
        var listToDelete = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));
        cardService.deleteCardsFromList(listId);
        listRepository.delete(listToDelete);
    }

    private ListDto convertToDto(List list) {
        return ListDto.builder()
                .id(list.getId())
                .name(list.getName())
                .cards(cardService.getCardsFromList(list.getId()))
                .build();
    }

}