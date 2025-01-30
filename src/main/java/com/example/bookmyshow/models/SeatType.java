package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
public class SeatType extends BaseModel {
    private String name;
}
