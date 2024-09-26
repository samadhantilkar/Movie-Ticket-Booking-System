package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.MoneyRequestDto;
import project.example.Movie_Booking.dtos.MoneyResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.User;
import project.example.Movie_Booking.repositories.UserRepository;

import java.util.Optional;

@Service
@Qualifier("userService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public MoneyResponseDto addMoney(MoneyRequestDto requestDto){
        Optional<User> userOptional=userRepository.findById(requestDto.getId());
        userOptional.get().setBalance(requestDto.getAmount());
        User user=userRepository.save(userOptional.get());
        MoneyResponseDto moneyResponseDto=new MoneyResponseDto();
        moneyResponseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return moneyResponseDto;
    }

    public User createUser(String email){
        User user=new User();
        user.setEmail(email);

        User savedUser=userRepository.save(user);

        return savedUser;
    }
}
