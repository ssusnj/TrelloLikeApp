package com.TrelloLikeApp.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDto {

    private Long id;
    private String title;
    private String description;
    private UserDto assignedTo;

}
