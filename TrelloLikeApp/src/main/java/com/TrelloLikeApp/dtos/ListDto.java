package com.TrelloLikeApp.dtos;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class ListDto {

    private Long id;
    private String name;
    private Set<CardDto> cards;

}