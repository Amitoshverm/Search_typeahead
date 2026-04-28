package com.bookmarks.TypeAhead.controller;

import com.bookmarks.TypeAhead.authentication.AuthenticationService;
import com.bookmarks.TypeAhead.dto.CreateUserDto;
import com.bookmarks.TypeAhead.dto.LoginUserDto;
import com.bookmarks.TypeAhead.dto.SignUpUserDto;
import com.bookmarks.TypeAhead.dto.UserResponseDto;
import com.bookmarks.TypeAhead.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService,  AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpUserDto signUpUserDto) throws Exception {
        return new ResponseEntity<>(this.authenticationService.signUp(signUpUserDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        return new ResponseEntity<>(
                this.authenticationService.signIn(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()),
                HttpStatus.OK);
    }

@PostMapping()
public ResponseEntity<UserResponseDto> createUserDto(@RequestBody CreateUserDto createUserDto) {
    return new ResponseEntity<>(this.userService.createUser(createUserDto),
            HttpStatus.CREATED);
}

@GetMapping()
public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    return new ResponseEntity<>(this.userService.getAllUsers(),
            HttpStatus.OK);
}

@GetMapping("{id}")
public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(this.userService.getUserById(id),
            HttpStatus.OK);
}

@DeleteMapping("{id}")
public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
    this.userService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

}

