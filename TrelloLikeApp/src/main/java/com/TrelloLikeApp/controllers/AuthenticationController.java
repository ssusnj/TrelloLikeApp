package com.TrelloLikeApp.controllers;
import com.TrelloLikeApp.configurations.UserAuthProvider;
import com.TrelloLikeApp.dtos.CredentialsDto;
import com.TrelloLikeApp.dtos.RegisterDto;
import com.TrelloLikeApp.dtos.UserDto;
import com.TrelloLikeApp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentials) { // todo: add @Valid
        var userDto = userService.login(credentials);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterDto user) {
        UserDto createdUserDto = userService.register(user);
        createdUserDto.setToken(userAuthProvider.createToken(createdUserDto));
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
}
