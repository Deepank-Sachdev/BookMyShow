package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Optional<Screen> findById(Long screenId);
}