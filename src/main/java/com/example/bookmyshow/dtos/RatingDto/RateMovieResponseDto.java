package com.example.bookmyshow.dtos.RatingDto;

import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.models.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateMovieResponseDto {
    private ResponseStatus responseStatus;
    private Rating rating;
}