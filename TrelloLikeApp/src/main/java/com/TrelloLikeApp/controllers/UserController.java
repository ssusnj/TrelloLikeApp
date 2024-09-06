package com.TrelloLikeApp.controllers;

import com.TrelloLikeApp.dtos.UserDto;
import com.TrelloLikeApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired // or allArgsConstructor
    private UserService userService;

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        var user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}