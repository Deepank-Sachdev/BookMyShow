package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.UserNotFound;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    /*
        1. Get user details from DB
        2. Get the show details from DB
        * ------Start Transaction -----*
        3. Get show seats from DB
        4. Check if seats are available
        5. if not, throw exception
        6. If yes, change the status to locked and update lockedAt
        * ------End Transaction -------*
        7. Return Booking object
     */

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculatorService priceCalculatorService;
    private BookingRepository bookingRepository;

    public BookingService(UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          PriceCalculatorService priceCalculatorService,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculatorService = priceCalculatorService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking issueTicket(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFound {

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFound("User not found");
        }
        User bookedby = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException(); //create new custom exception
        }
        Show show = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        for(ShowSeat showSeat : showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED)) &&
                        Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes() > 15)) {
                throw new RuntimeException();
            }
        }

        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setLockedAt(new Date());
            showSeatRepository.save(showSeat); //saveAll() method
        }

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setUser(bookedby);
        booking.setBookedAt(new Date());
        booking.setShowSeats(showSeats);
        booking.setShow(show);
        booking.setAmount(priceCalculatorService.calculatePrice(show, showSeats));


        return bookingRepository.save(booking);
    }
}
