package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterUserRequestDto;
import project.example.Movie_Booking.dtos.RegisterUserResponseDto;
import project.example.Movie_Booking.models.User;
import project.example.Movie_Booking.services.UserService;
//Spring Container
@Controller
public class UserController {
    @Qualifier("UserService")
    private UserService userService;

// IOC Inversion Of Controller
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto request){
        User savedUser=userService.createUser(
                request.getEmail()
        );

        RegisterUserResponseDto response=new RegisterUserResponseDto();
        return response;
    }

}
