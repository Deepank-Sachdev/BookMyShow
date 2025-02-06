package com.example.bookmyshow.services;

import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String name, String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RuntimeException("User already exists"); // Add custom exception
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);

//        BCryptPasswordEncoder encryptedPassword = new BCryptPasswordEncoder();
//        user.setPassword(encryptedPassword.encode(password));

        user.setPassword(password);

        return userRepository.save(user);
    }
//
//    public boolean login(String email, String password) throws Exception {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()){
//            throw new Exception("User doesn't exists. Please Signup");
//        }
//
//        if(BCrypt.checkpw(password, userOptional.get().getPassword()) == true){
//            return true;
//        }
//        return false;
//    }
}
