package com.TrelloLikeApp.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

}