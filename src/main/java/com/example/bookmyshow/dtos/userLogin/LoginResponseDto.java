package com.example.bookmyshow.dtos.userLogin;

import com.example.bookmyshow.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponseDto {
    private boolean loggedIn;
    private ResponseStatus responseStatus;
}