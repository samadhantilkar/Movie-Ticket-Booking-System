package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.models.User;
import project.example.Movie_Booking.repositories.UserRepository;

@Service
@Qualifier("userService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(String email){
        User user=new User();
        user.setEmail(email);

        User savedUser=userRepository.save(user);

        return savedUser;
    }
}
