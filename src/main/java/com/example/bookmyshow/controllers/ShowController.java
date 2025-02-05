package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.CreateShowDto.CreateShowRequestDTO;
import com.example.bookmyshow.dtos.CreateShowDto.CreateShowResponseDTO;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.exceptions.*;
import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.services.CreateShowService;
import org.springframework.stereotype.Controller;

@Controller
public class ShowController {
    private CreateShowService createShowService;

    public ShowController(CreateShowService createShowService) {
        this.createShowService = createShowService;
    }

    public CreateShowResponseDTO createShow(CreateShowRequestDTO requestDTO) {
        CreateShowResponseDTO responseDTO = new CreateShowResponseDTO();
        try{
            Show createdShow = createShowService.createShow(
                    requestDTO.getUserId(),
                    requestDTO.getMovieId(),
                    requestDTO.getScreenId(),
                    requestDTO.getStartTime(),
                    requestDTO.getEndTime(),
                    requestDTO.getPricingConfig(),
                    requestDTO.getFeatures()
            );
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            responseDTO.setShow(createdShow);
        }
        catch (MovieNotFound | ScreenNotFoundException | FeatureNotSupportedByScreen |
               InvalidDateException | UserNotFound | UnAuthorizedAccessException ex){
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }
}