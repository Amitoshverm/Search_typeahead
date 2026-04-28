package com.bookmarks.TypeAhead.authentication;

import com.bookmarks.TypeAhead.Exceptions.UserDoesNotExists;
import com.bookmarks.TypeAhead.dto.SignUpUserDto;
import com.bookmarks.TypeAhead.dto.UserResponseDto;
import com.bookmarks.TypeAhead.entity.Users;
import com.bookmarks.TypeAhead.repository.UserRepository;
import com.bookmarks.TypeAhead.service.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDto signUp(SignUpUserDto signUpUserDto) throws Exception{
        Optional<Users> user = this.userRepository.findByEmail(signUpUserDto.getEmail());

        if (user.isPresent()) {
            throw new RuntimeException("User Already Exists");
        }

//        Users getUser = user.get();
        String hashedPassword = this.bCryptPasswordEncoder.encode(signUpUserDto.getPassword());

        Users userForDb = new Users();
        userForDb.setDisplayName(signUpUserDto.getUsername());
        userForDb.setEmail(signUpUserDto.getEmail());
        userForDb.setPassword(hashedPassword);
        userRepository.save(userForDb);

        String token = this.jwtService.generateToken(userForDb.getEmail());

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setDisplayName(userForDb.getDisplayName());
        userResponseDto.setEmail(userForDb.getEmail());
        userResponseDto.setToken(token);
        return userResponseDto;
    }

    public UserResponseDto signIn(String email, String password) {
        Optional<Users> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserDoesNotExists("User doesn't exists");
        }

        Users userForDb = user.get();

        if (!bCryptPasswordEncoder.matches(password, userForDb.getPassword())){
            throw new IllegalArgumentException("Invalid Credentials");
        }

        String token = this.jwtService.generateToken(userForDb.getEmail());

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setDisplayName(userForDb.getDisplayName());
        userResponseDto.setEmail(userForDb.getEmail());
        userResponseDto.setToken(token);
        return  userResponseDto;
    }
}
