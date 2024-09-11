package com.TrelloLikeApp.services;

import com.TrelloLikeApp.dtos.CardDto;
import com.TrelloLikeApp.entity.Card;
import com.TrelloLikeApp.repositories.CardRepository;
import com.TrelloLikeApp.repositories.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardService {

    private final ListRepository listRepository;
    private final CardRepository cardRepository;

    public CardDto createCardTitle(String title, Long listId) {
        var list =  listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found."));
        var card = new Card();
        card.setTitle(title);
        card.setList(list);
        var createdCard = cardRepository.save(card);
        return convertToDto(createdCard);
    }

    public CardDto updateCard(Long cardId, String title, String description, Long listId) {
        var list =  listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found."));
        var cardToUpdate = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found."));
        cardToUpdate.setTitle(title == null ? cardToUpdate.getTitle() : title);
        cardToUpdate.setDescription(description);
        cardToUpdate.setList(list);
        var createdCard = cardRepository.save(cardToUpdate);
        return convertToDto(createdCard);
    }

    public Set<CardDto> getCardsFromList(Long listId) {
        var cards = cardRepository.findAllByList_Id(listId)
                .orElseThrow(() -> new RuntimeException("List not found."));
        return cards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    public void deleteCardsFromList(Long listId) {
        cardRepository.deleteAllByList_Id(listId);
    }

    public CardDto deleteCard(Long listId, Long cardId) {
        var cardToDelete = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found."));
        cardRepository.delete(cardToDelete);
        return convertToDto(cardToDelete);
    }

    private CardDto convertToDto(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .build();
    }
}