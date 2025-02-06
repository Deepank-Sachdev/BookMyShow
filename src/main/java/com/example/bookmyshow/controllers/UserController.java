package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.dtos.userLogin.LoginRequestDto;
import com.example.bookmyshow.dtos.userLogin.LoginResponseDto;
import com.example.bookmyshow.dtos.userLogin.SignUpUserRequestDto;
import com.example.bookmyshow.dtos.userLogin.SignupUserResponseDTO;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User signUp(SignUpUserRequestDto signUpUserRequestDto) {
        //Add try Catch
        return userService.signUp(signUpUserRequestDto.getName(),
                            signUpUserRequestDto.getEmail(),
                                signUpUserRequestDto.getPassword());
    }

//    public SignupUserResponseDTO signupUser(SignUpUserRequestDto requestDTO){
//        SignupUserResponseDTO responseDTO = new SignupUserResponseDTO();
//        try {
//            User user = userService.signUp(requestDTO.getName(),
//                    requestDTO.getEmail(), requestDTO.getPassword());
//            responseDTO.setName(user.getName());
//            responseDTO.setEmail(user.getEmail());
//            responseDTO.setUserId(user.getId());
//            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
//        } catch (Exception e) {
//            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
//        }
//        return responseDTO;
//    }
//
//    public LoginResponseDto login(LoginRequestDto requestDto){
//        LoginResponseDto responseDto = new LoginResponseDto();
//        try {
//            Boolean user = userService.login(requestDto.getEmail(), requestDto.getPassword());
//            if (user){
//                responseDto.setLoggedIn(true);
//                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
//            }
//            else {
//                responseDto.setLoggedIn(false);
//                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
//            }
//        } catch (Exception e) {
//
//            responseDto.setResponseStatus(ResponseStatus.FAILURE);
//        }
//        return responseDto;
//    }
}
