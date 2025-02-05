package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.*;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.*;
import org.hibernate.Hibernate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CreateShowService {

    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private SeatsRepository seatsRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;

    public CreateShowService(MovieRepository movieRepository, ScreenRepository screenRepository,
                            SeatsRepository seatsRepository, ShowSeatTypeRepository showSeatTypeRepository,
                                ShowRepository showRepository, ShowSeatRepository showSeatRepository,
                                    UserRepository userRepository){
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatsRepository = seatsRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
    }


    public Show createShow(Long userId, Long movieId, Long screenId, Date startTime, Date endTime,
                           List<Pair<SeatType, Integer>> pricingConfig, List<Feature> features)
            throws MovieNotFound, ScreenNotFoundException, FeatureNotSupportedByScreen, InvalidDateException,
            UserNotFound, UnAuthorizedAccessException {
                
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new UserNotFound("User not found");
        }
        if (userOptional.get().getUserType() != UserType.ADMIN){
            throw new UnAuthorizedAccessException("Only admins are allowed");
        }
        
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()){
            throw new MovieNotFound("Movie not found");
        }
        Optional<Screen> screenOptional = screenRepository.findById(screenId);
        if (screenOptional.isEmpty()) {
            throw new ScreenNotFoundException("Screen is not found");
        }

        Hibernate.initialize(screenOptional.get().getFeatures());

        if (screenOptional.get().getFeatures() == null || screenOptional.get().getFeatures().isEmpty()){
            throw new FeatureNotSupportedByScreen("Screen does not support any features");
        }
        for (Feature f : features){
            if(!screenOptional.get().getFeatures().contains(f)){
                throw new FeatureNotSupportedByScreen("Screen doesn't support following features :" + f);
            }
        }

        if(startTime.after(endTime)){
            throw new InvalidDateException("Start time should be before end time");
        }
        if (startTime.before(new Date(System.currentTimeMillis()))){
            throw new InvalidDateException("Show can't be created for previous date");
        }

        // Creating new show after validations
        Show show = new Show();
        show.setMovie(movieOptional.get());
        if (screenOptional.get().getStatus() == ScreenStatus.OPERATIONAL){
            show.setScreen(screenOptional.get());
        }
        else throw new ScreenNotFoundException("Screen status:" + screenOptional.get().getStatus());
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setFeatures(features);
        showRepository.save(show);


        for (Pair<SeatType, Integer> p : pricingConfig){
            ShowSeatType showSeatType = new ShowSeatType();
            showSeatType.setShow(show);
            showSeatType.setSeatType(p.getFirst());
            showSeatType.setPrice(p.getSecond());
            showSeatTypeRepository.save(showSeatType);
        }

        List<Seat> seats = new ArrayList<>();
        List<Seat> allSeats = seatsRepository.findAll();

        for (Seat seat : allSeats) {
            if (seat.getScreen().getId().equals(screenId)) {
                seats.add(seat);
            }
        }
        for (Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }
        
        
        return show;
    }
    
}