package com.bookmarks.TypeAhead.service;

import java.util.List;

import com.bookmarks.TypeAhead.Exceptions.NoUsersFoundException;
import com.bookmarks.TypeAhead.Exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import com.bookmarks.TypeAhead.dto.CreateUserDto;
import com.bookmarks.TypeAhead.dto.UserResponseDto;
import com.bookmarks.TypeAhead.entity.Users;
import com.bookmarks.TypeAhead.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Users user =  this.userRepository.
                findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setDisplayName(user.getUsername());
        userResponseDto.setEmail(user.getEmail());

        return userResponseDto;
    }

    @Override
    public UserResponseDto createUser(CreateUserDto createUserDto) {
//        Users user = new Users();
//        user.getDisplayName(createUserDto.getDisplayName());
//        user.setEmail(createUserDto.getEmail());
//        user.setPassword(createUserDto.getPassword());
//
//        Users savedUser = this.userRepository.save(user);
//
//        UserResponseDto userResponseDto = new UserResponseDto();
//        userResponseDto.setUsername(savedUser.getUsername());
//        userResponseDto.setEmail(savedUser.getEmail());
//
//        return userResponseDto;
    return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<Users> users = this.userRepository.findAll();

        if (users.isEmpty()) {
            throw new NoUsersFoundException("No users found");
        }

        return users.stream().map(user-> {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setDisplayName(user.getUsername());
            userResponseDto.setEmail(user.getEmail());
            return userResponseDto;
        }).toList();
    }

    @Override
    public void deleteUserById(Long id) {
        Users user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        this.userRepository.deleteById(id);
    }


}
