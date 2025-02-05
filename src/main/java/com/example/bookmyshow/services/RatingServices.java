package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.MovieNotFound;
import com.example.bookmyshow.exceptions.UserNotFound;
import com.example.bookmyshow.models.Movie;
import com.example.bookmyshow.models.Rating;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.RatingRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServices{

    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;
    private UserRepository userRepository;

    public RatingServices(MovieRepository movieRepository,
                             RatingRepository ratingRepository,
                             UserRepository userRepository){
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    public Rating rateMovie(Long userId, Long movieId, Long rating) throws UserNotFound, MovieNotFound {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFound("User not found");
        }
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()){
            throw new MovieNotFound("Movie not found");
        }
        if (rating < 1 || rating > 5){
            throw new RuntimeException("Rating should be on a scale of 1 to 5");
        }
        Optional<Rating> RateOptional = ratingRepository.findByUserIdAndMovieId(userId, movieId);
        Rating rate;
        if (RateOptional.isEmpty()){
            rate = new Rating();
            rate.setMovie(movieOptional.get());
            rate.setRating(rating);
            rate.setUser(userOptional.get());
        }
        else{
            rate = RateOptional.get();
            rate.setRating(rating);
        }

        return ratingRepository.save(rate);
    }

    public double getAverageRating(Long movieId) throws MovieNotFound {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()){
            throw new MovieNotFound("Movie not found");
        }
        List<Rating> ratingOptional = ratingRepository.findByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFound("No ratings found for this movie"));
        double avgRating = 0;
        for(Rating R : ratingOptional){
            avgRating += R.getRating();
        }
        avgRating = avgRating/ratingOptional.size();

        return avgRating;
    }

}
