package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterTheatreRequestDto;
import project.example.Movie_Booking.dtos.RegisterTheatreResponseDto;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.services.TheatreService;

@Controller
public class TheatreController {

    private TheatreService theatreService;
    @Autowired
    public TheatreController(TheatreService theatreService){
        this.theatreService=theatreService;
    }

    public RegisterTheatreResponseDto registerTheatre(RegisterTheatreRequestDto requestDto) throws TheatreNotFound {
        return this.theatreService.registerTheatre(requestDto);
    }
}
