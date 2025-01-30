package com.example.bookmyshow;

import com.example.bookmyshow.controllers.BookingController;
import com.example.bookmyshow.controllers.UserController;
import com.example.bookmyshow.dtos.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {
	@Autowired
//	private BookingController bookingController;
	private UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setName("Deepank");
		userRequestDto.setEmail("deepank@gmail.com");
		userRequestDto.setPassword("pass@word");

		userController.signUp(userRequestDto);
	}
}
