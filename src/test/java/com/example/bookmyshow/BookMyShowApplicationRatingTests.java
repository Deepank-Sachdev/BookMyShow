package com.example.bookmyshow;

import com.example.bookmyshow.controllers.RatingController;
import com.example.bookmyshow.dtos.RatingDto.*;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.models.Movie;
import com.example.bookmyshow.models.Rating;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.RatingRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookMyShowApplicationRatingTests {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RatingController ratingsController;
	private User user1, user2;
	private Movie movie;

	@BeforeEach
	public void insertDummyData(){
		user1 = new User();
		user1.setName("John Doe");
		user1.setEmail("john@doe.com");
		user1 = userRepository.save(user1);

		user2 = new User();
		user2.setName("Jane Doe");
		user2.setEmail("jane@doe.com");
		user2 = userRepository.save(user2);

		movie = new Movie();
		movie.setTitle("The Dark Knight");
		movie.setDescription("A movie about Batman");
		movie = movieRepository.save(movie);

	}

	@AfterEach
	public void cleanUp(){
		ratingRepository.deleteAll();
		movieRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void testRateMovie_1stRatingByUser_Success(){
		RateMovieRequestDto requestDto = new RateMovieRequestDto();
		requestDto.setUserId(user1.getId());
		requestDto.setMovieId(movie.getId());
		requestDto.setRating(5L);

		RateMovieResponseDto rateMovieResponseDto = ratingsController.rateMovie(requestDto);
		assertNotNull(rateMovieResponseDto, "Response should be not null");
		assertEquals(ResponseStatus.SUCCESS, rateMovieResponseDto.getResponseStatus(), "Response status should be SUCCESS");

		List<Rating> ratings = ratingRepository.findAll();
		assertEquals(1, ratings.size(), "There should be 1 rating in the database");
		Rating rating = ratings.get(0);
		assertEquals(user1.getId(), rating.getUser().getId(), "User id should be same");
		assertEquals(movie.getId(), rating.getMovie().getId(), "Movie id should be same");
		assertEquals(5, rating.getRating(), "Rating should be 5");
	}

	@Test
	public void testRateMovie_DoubleRatingByUser_Success(){
		RateMovieRequestDto requestDto = new RateMovieRequestDto();
		requestDto.setUserId(user1.getId());
		requestDto.setMovieId(movie.getId());
		requestDto.setRating(5L);

		RateMovieResponseDto rateMovieResponseDto = ratingsController.rateMovie(requestDto);

		//Rating for the 2nd time
		requestDto.setRating(4L);
		rateMovieResponseDto = ratingsController.rateMovie(requestDto);

		assertNotNull(rateMovieResponseDto, "Response should be not null");
		assertEquals(ResponseStatus.SUCCESS, rateMovieResponseDto.getResponseStatus(), "Response status should be SUCCESS");

		List<Rating> ratings = ratingRepository.findAll();
		assertEquals(1, ratings.size(), "There should be 1 rating in the database");
		Rating rating = ratings.get(0);
		assertEquals(user1.getId(), rating.getUser().getId(), "User id should be same");
		assertEquals(movie.getId(), rating.getMovie().getId(), "Movie id should be same");
		assertEquals(4, rating.getRating(), "Rating should be 5");
	}

	@Test
	public void testRateMovie_UserNotFound_Failure(){
		RateMovieRequestDto requestDto = new RateMovieRequestDto();
		requestDto.setUserId(100L);
		requestDto.setMovieId(movie.getId());
		requestDto.setRating(5L);

		RateMovieResponseDto rateMovieResponseDto = ratingsController.rateMovie(requestDto);
		assertNotNull(rateMovieResponseDto, "Response should be not null");
		assertEquals(ResponseStatus.FAILURE, rateMovieResponseDto.getResponseStatus(), "Response status should be FAILURE");

		List<Rating> ratings = ratingRepository.findAll();
		assertEquals(0, ratings.size(), "There should be 0 rating in the database");
	}

	@Test
	public void testRateMovie_MovieNotFound_Failure(){
		RateMovieRequestDto requestDto = new RateMovieRequestDto();
		requestDto.setUserId(user1.getId());
		requestDto.setMovieId(movie.getId() * 100);
		requestDto.setRating(5L);

		RateMovieResponseDto rateMovieResponseDto = ratingsController.rateMovie(requestDto);
		assertNotNull(rateMovieResponseDto, "Response should be not null");
		assertEquals(ResponseStatus.FAILURE, rateMovieResponseDto.getResponseStatus(), "Response status should be FAILURE");

		List<Rating> ratings = ratingRepository.findAll();
		assertEquals(0, ratings.size(), "There should be 0 rating in the database");
	}


	@Test
	public void testGetAverageMovieRating_Success(){
		//User 1 rates movie
		RateMovieRequestDto requestDto = new RateMovieRequestDto();
		requestDto.setUserId(user1.getId());
		requestDto.setMovieId(movie.getId());
		requestDto.setRating(5L);
		ratingsController.rateMovie(requestDto);

		//User 2 rates movie
		requestDto.setUserId(user2.getId());
		requestDto.setRating(4L);
		ratingsController.rateMovie(requestDto);

		// Get average rating
		GetAverageMovieRequestDto getAverageMovieRequestDto = new GetAverageMovieRequestDto();
		getAverageMovieRequestDto.setMovieId(movie.getId());
		GetAverageMovieResponseDto getAverageMovieResponseDto = ratingsController.getAverageMovieRating(getAverageMovieRequestDto);
		assertNotNull(getAverageMovieResponseDto, "Response should be not null");
		assertEquals(ResponseStatus.SUCCESS, getAverageMovieResponseDto.getResponseStatus(), "Response status should be SUCCESS");
		assertEquals(4.5, getAverageMovieResponseDto.getAverageRating(), 0.01,"Average rating should be 4.5");
	}

	@Test
	public void testGetAverageMovieRating_MovieNotFound_Failure(){
		//User 1 rates movie
		RateMovieRequestDto requestDto = new RateMovieRequestDto();
		requestDto.setUserId(user1.getId());
		requestDto.setMovieId(movie.getId());
		requestDto.setRating(5L);
		ratingsController.rateMovie(requestDto);

		//User 2 rates movie
		requestDto.setUserId(user2.getId());
		requestDto.setRating(4L);
		ratingsController.rateMovie(requestDto);

		// Get average rating
		GetAverageMovieRequestDto getAverageMovieRequestDto = new GetAverageMovieRequestDto();
		getAverageMovieRequestDto.setMovieId(movie.getId()*100);
		GetAverageMovieResponseDto getAverageMovieResponseDto = ratingsController.getAverageMovieRating(getAverageMovieRequestDto);
		assertNotNull(getAverageMovieResponseDto, "Response should be not null");
		assertEquals(ResponseStatus.FAILURE, getAverageMovieResponseDto.getResponseStatus(), "Response status should be FAILURE");
	}


@Test
	void contextLoads() {
	}

}
