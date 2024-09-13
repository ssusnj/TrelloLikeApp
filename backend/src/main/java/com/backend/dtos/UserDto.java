package com.backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String token;

}