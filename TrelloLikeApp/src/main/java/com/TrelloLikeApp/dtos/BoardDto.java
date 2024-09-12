package com.TrelloLikeApp.dtos;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class BoardDto {

    private Long id;
    private String name;
    private String description;
    private UserDto createdBy;
    private Set<ListDto> lists;

}