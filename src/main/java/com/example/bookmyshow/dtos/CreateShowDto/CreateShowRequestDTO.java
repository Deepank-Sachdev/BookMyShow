package com.example.bookmyshow.dtos.CreateShowDto;

import com.example.bookmyshow.models.Feature;
import com.example.bookmyshow.models.SeatType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateShowRequestDTO {
    private Long movieId;
    private Long userId;
    private Long screenId;
    private Date startTime;
    private Date endTime;
    private List<Feature> features;
    private List<Pair<SeatType, Integer>> pricingConfig;
}