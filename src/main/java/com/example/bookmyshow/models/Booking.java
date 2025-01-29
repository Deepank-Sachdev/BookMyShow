package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
public class Booking extends BaseModel{
    private User user;
    private BookingStatus bookingStatus;
    private Date bookingDate;
    private int amount;
    private Show show;
    private List<ShowSeat> showSeats;
    private List<Payment> payments;
}
