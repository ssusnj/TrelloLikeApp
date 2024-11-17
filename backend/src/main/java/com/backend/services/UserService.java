package com.backend.services;

import com.backend.dtos.CredentialsDto;
import com.backend.dtos.RegisterDto;
import com.backend.dtos.UserDto;
import com.backend.entity.User;

public interface UserService {

    UserDto login(CredentialsDto credentials);
    UserDto register(RegisterDto userDto);
    UserDto getUserById(Long id);
    User getUserByUsername(String username); // DTO!
    // delete

}
