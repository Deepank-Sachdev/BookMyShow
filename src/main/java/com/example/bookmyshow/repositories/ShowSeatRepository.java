package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository <ShowSeat, Long> {
    List<ShowSeat> findAllByIdIn(List<Long> showSeatIds);
}
