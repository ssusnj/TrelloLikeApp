package com.TrelloLikeApp.services;

import com.TrelloLikeApp.dtos.CredentialsDto;
import com.TrelloLikeApp.dtos.SignUpDto;
import com.TrelloLikeApp.dtos.UserDto;
import com.TrelloLikeApp.entity.Role;
import com.TrelloLikeApp.entity.User;
import com.TrelloLikeApp.repositories.RoleRepository;
import com.TrelloLikeApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto login(CredentialsDto credentials) {
        var user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new RuntimeException("No user with username: " + credentials.username()));
        if (passwordEncoder.matches(CharBuffer.wrap(credentials.password()), user.getPassword())) {
            return convertToDto(user);
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto register(SignUpDto userDto) {
        var optionalUser = userRepository.findByUsername(userDto.username());
        if (optionalUser.isPresent()) {
                throw new RuntimeException("Username already exists!"); // bad request
        }
        var role = roleRepository.findByRoleType(Role.RoleType.valueOf("USER")).orElseThrow(
                () -> new RuntimeException("This role does not exist!")
        );
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setUsername(userDto.username());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(Arrays.toString(userDto.password())));
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto findByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public Set<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole().getRoleType().name())
                .build();
    }
}