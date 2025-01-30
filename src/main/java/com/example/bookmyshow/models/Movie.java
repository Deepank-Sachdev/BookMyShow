package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.Book;

@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    private String title;
}
