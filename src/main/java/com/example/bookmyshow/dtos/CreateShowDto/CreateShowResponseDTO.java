package com.example.bookmyshow.dtos.CreateShowDto;

import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.models.Show;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShowResponseDTO {
    private ResponseStatus responseStatus;
    private Show show;
}