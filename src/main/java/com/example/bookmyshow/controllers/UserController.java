package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.UserRequestDto;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User signUp(UserRequestDto userRequestDto) {
        //Add try Catch
        return userService.signUp(userRequestDto.getName(),
                            userRequestDto.getEmail(),
                                userRequestDto.getPassword());
    }
}
