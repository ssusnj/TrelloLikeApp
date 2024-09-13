package com.backend.services;

import com.backend.dtos.CredentialsDto;
import com.backend.dtos.RegisterDto;
import com.backend.dtos.UserDto;
import com.backend.entity.User;
import com.backend.exceptions.AppException;
import com.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto login(CredentialsDto credentials) {
        var user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new AppException("No user with username: " + credentials.username(), HttpStatus.BAD_REQUEST));
        if (passwordEncoder.matches(CharBuffer.wrap(credentials.password()), user.getPassword())) {
            return convertToDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(RegisterDto userDto) {
        var optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
                throw new AppException("Username already exists!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        return convertToDto(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User " + username + " not found", HttpStatus.NOT_FOUND));
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .username(user.getUsername())
                .build();
    }
}