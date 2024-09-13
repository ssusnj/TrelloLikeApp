package com.backend.controllers;

import com.backend.dtos.CardDto;
import com.backend.dtos.ListDto;
import com.backend.services.CardService;
import com.backend.services.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards/{username}/{boardId}")
@CrossOrigin(origins = "http://localhost:4200")
public class ListController {

    private final ListService listService;
    private final CardService cardService;

    @PostMapping()
    public ResponseEntity<ListDto> addList(@PathVariable Long boardId, @RequestBody ListDto list) {
        var createdList = listService.createList(list.getName(), boardId);
        return new ResponseEntity<>(createdList, HttpStatus.OK);
    }

    @DeleteMapping("/{listId}")
    @Transactional
    public ResponseEntity<ListDto> deleteList(@PathVariable Long listId) {
        var deletedList = listService.deleteList(listId);
        return new ResponseEntity<>(deletedList, HttpStatus.OK);
    }

    @PostMapping("/{listId}")
    public ResponseEntity<CardDto> addCardToList(@PathVariable Long listId, @RequestBody String cardTitle) {
        var createdCard = cardService.createCardTitle(cardTitle, listId);
        return new ResponseEntity<>(createdCard, HttpStatus.OK);
  }

}