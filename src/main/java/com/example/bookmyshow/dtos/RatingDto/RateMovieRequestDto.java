package com.example.bookmyshow.dtos.RatingDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateMovieRequestDto {
    private Long userId;
    private Long movieId;
    private Long rating;
}