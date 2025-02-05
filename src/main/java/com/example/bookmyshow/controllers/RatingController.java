package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.RatingDto.*;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.exceptions.MovieNotFound;
import com.example.bookmyshow.exceptions.UserNotFound;
import com.example.bookmyshow.models.Rating;
import com.example.bookmyshow.services.RatingServices;
import org.springframework.stereotype.Controller;

@Controller
public class RatingController {
    private RatingServices ratingsService;

    public RatingController(RatingServices ratingsService){
        this.ratingsService = ratingsService;
    }

    public RateMovieResponseDto rateMovie(RateMovieRequestDto requestDto){
        RateMovieResponseDto responseDto = new RateMovieResponseDto();
        try {
            Rating rating = ratingsService.rateMovie(requestDto.getUserId(),requestDto.getMovieId(), requestDto.getRating());
            responseDto.setRating(rating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } 
        catch (UserNotFound | MovieNotFound e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public GetAverageMovieResponseDto getAverageMovieRating(GetAverageMovieRequestDto requestDto){
        GetAverageMovieResponseDto responseDto = new GetAverageMovieResponseDto();
        try {
            double avgRating = ratingsService.getAverageRating(requestDto.getMovieId());
            responseDto.setAverageRating(avgRating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } 
        catch (MovieNotFound e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
