package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findAllByShow(Show show);
}
