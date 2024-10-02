package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.InvalidEmailException;
import project.example.Movie_Booking.exceptions.InvalidPasswordException;
import project.example.Movie_Booking.services.UserService;

@Controller
public class UserController {

    @Qualifier("UserService")
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto request) {
        try{
            validateRegistrationRequest(request);
            RegisterUserResponseDto savedUser = userService.createUser(request);
            return savedUser;
        }catch (Exception e){
            System.out.println(e.getMessage());
            RegisterUserResponseDto responseDto=new RegisterUserResponseDto();
            responseDto.setStatus(ResponseDtoStatus.FAILURE);
            return responseDto;
        }
    }

    public UpdateUserResponseDto updateUser(UpdateUserRequestDto userRequestDto){
        try {
            validateUpdateRequest(userRequestDto);
            return userService.updateUser(userRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            UpdateUserResponseDto updateUserResponseDto=new UpdateUserResponseDto();
            updateUserResponseDto.setStatus(ResponseDtoStatus.FAILURE);
            return updateUserResponseDto;
        }
    }

    private void validateRegistrationRequest(RegisterUserRequestDto request)throws Exception{
        if (request.getEmail() == null) {
            throw new InvalidEmailException("Email cannot be null");
        }
        if (request.getPassword() == null) {
            throw new InvalidPasswordException("Password cannot be null");
        }
        if (request.getPassword().length() < 8) {
            throw new InvalidPasswordException("Password should be at least 8 characters");
        }
    }

    private void validateUpdateRequest(UpdateUserRequestDto userRequestDto) throws Exception {
        if (userRequestDto.getEmail() == null) {
            throw new InvalidEmailException("Email cannot be null");
        }
        if (userRequestDto.getOldPassword() == null) {
            throw new InvalidPasswordException("Old Password cannot be null");
        }
        if (userRequestDto.getOldPassword().length() < 8) {
            throw new InvalidPasswordException("Old Password should be at least 8 characters");
        }
        if (userRequestDto.getNewPassword() == null) {
            throw new InvalidPasswordException("New Password cannot be null");
        }
        if (userRequestDto.getNewPassword().length() < 8) {
            throw new InvalidPasswordException("New Password should be at least 8 characters");
        }
    }
}
