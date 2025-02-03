package com.example.bookmyshow.dtos.RatingDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAverageMovieResponseDto {
    private ResponseStatus responseStatus;
    private double averageRating;
}