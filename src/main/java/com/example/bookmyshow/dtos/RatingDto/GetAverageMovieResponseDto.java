package com.example.bookmyshow.dtos.RatingDto;

import com.example.bookmyshow.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAverageMovieResponseDto {
    private ResponseStatus responseStatus;
    private double averageRating;
}