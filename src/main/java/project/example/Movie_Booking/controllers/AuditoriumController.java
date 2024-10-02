package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterAuditoriumResponseDto;
import project.example.Movie_Booking.dtos.RegisterTheatreResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidAuditoriumFeatures;
import project.example.Movie_Booking.exceptions.InvalidSeatCapacityException;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.models.Auditorium;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.services.AuditoriumService;

import java.util.Optional;

@Controller
public class AuditoriumController {

    AuditoriumService auditoriumService;
    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService){
        this.auditoriumService=auditoriumService;
    }

    public RegisterAuditoriumResponseDto createAuditorium(RegisterAuditoriumRequestDto requestDto)  {
        try {
            validateCreateAuditorium(requestDto);
            return this.auditoriumService.addAuditorium(requestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            RegisterAuditoriumResponseDto responseDto=new RegisterAuditoriumResponseDto();
            responseDto.setStatus(ResponseDtoStatus.FAILURE);
            return responseDto;
        }
    }

    private void validateCreateAuditorium(RegisterAuditoriumRequestDto requestDto)throws Exception{
        if(requestDto.getCapacity()<30){
            throw new InvalidSeatCapacityException("Seat capacity must be greater than 30");
        }
        if(requestDto.getAuditoriumFeatures().isEmpty()){
            throw new InvalidAuditoriumFeatures("Auditorium Features Not Be Empty");
        }
    }
}
