package com.backend.services;

import com.backend.dtos.CardDto;

import java.util.Set;

public interface CardService {

    CardDto createCardTitle(String title, Long listId);
    CardDto updateCard(Long cardId, CardDto cardDto, Long listId);
    Set<CardDto> getCardsFromList(Long listId);
    void deleteCardsFromList(Long listId);
    CardDto deleteCard(Long cardId);
}
