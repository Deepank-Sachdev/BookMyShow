package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAll();
    List<Seat> findByScreenId(Long screenId);

}