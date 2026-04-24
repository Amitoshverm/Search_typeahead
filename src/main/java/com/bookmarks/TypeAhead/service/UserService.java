package com.bookmarks.TypeAhead.service;

import com.bookmarks.TypeAhead.dto.CreateUserDto;
import com.bookmarks.TypeAhead.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto getUserById(Long id);
    UserResponseDto createUser(CreateUserDto createUserDto);
    List<UserResponseDto> getAllUsers();
    void deleteUserById(Long id);
}
