package com.backend.controllers;
import com.backend.configurations.UserAuthProvider;
import com.backend.dtos.CredentialsDto;
import com.backend.dtos.RegisterDto;
import com.backend.dtos.UserDto;
import com.backend.services.UserService;
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
    public ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentials) {
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
