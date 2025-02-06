package com.example.bookmyshow.dtos.userLogin;

import com.example.bookmyshow.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupUserResponseDTO {
    private ResponseStatus responseStatus;
    private String name;
    private String email;
    private Long userId;
}