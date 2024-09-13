package com.backend.services;

import com.backend.dtos.CredentialsDto;
import com.backend.dtos.RegisterDto;
import com.backend.dtos.UserDto;
import com.backend.entity.User;
import com.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("No user with username: " + credentials.username()));
        if (passwordEncoder.matches(CharBuffer.wrap(credentials.password()), user.getPassword())) {
            return convertToDto(user);
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto register(RegisterDto userDto) {
        var optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
                throw new RuntimeException("Username already exists!"); // bad request
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
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User " + username + " not found"));
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