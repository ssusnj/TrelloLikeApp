package com.TrelloLikeApp.dtos;

import lombok.Builder;
import lombok.Data;

@Data // for json to serialize
@Builder
public class BoardDto {

    private Long id;
    private String name;
    private String description;
    private String createdBy;

}