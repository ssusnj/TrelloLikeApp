package com.backend.services;

import com.backend.dtos.ListDto;

import java.util.Set;

public interface ListService {

    ListDto createList(String name, Long boardId);
    Set<ListDto> getListsFromBoard(Long boardId);
    void deleteListsFromBoard(Long boardId);
    ListDto deleteList(Long listId);
}
