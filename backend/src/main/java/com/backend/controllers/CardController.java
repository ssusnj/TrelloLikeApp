package com.backend.controllers;

import com.backend.dtos.CardDto;
import com.backend.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards/{username}/{boardId}/{listId}")
@CrossOrigin(origins = "http://localhost:4200")
public class CardController {

    private final CardService cardService;

    @PutMapping("/{cardId}")
    public ResponseEntity<CardDto> populateCard(@PathVariable Long listId, @PathVariable Long cardId, @RequestBody CardDto card) {
        var createdCard = cardService.updateCard(cardId, card.getTitle(), card.getDescription(), listId);
        return new ResponseEntity<>(createdCard, HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CardDto> removeCardFromList(@PathVariable Long cardId) {
        CardDto cartDto = cardService.deleteCard(cardId);
        return ResponseEntity.ok().body(cartDto);
    }
}
