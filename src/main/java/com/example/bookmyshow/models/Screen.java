package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

public class Screen extends BaseModel {
    private String name;

    @OneToMany (mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Seat> seats;

    private ScreenStatus status;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection (fetch = FetchType.EAGER)
    private List<Feature> features;
}
