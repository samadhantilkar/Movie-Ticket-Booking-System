package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterAuditoriumResponseDto;
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

    public RegisterAuditoriumResponseDto createAuditorium(RegisterAuditoriumRequestDto requestDto) throws TheatreNotFound {
        return this.auditoriumService.addAuditorium(requestDto);
    }
}
