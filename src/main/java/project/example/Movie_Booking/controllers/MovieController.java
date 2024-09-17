package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterActorRequestDto;
import project.example.Movie_Booking.dtos.RegisterMovieRequestDto;
import project.example.Movie_Booking.dtos.RegisterMovieResponseDto;
import project.example.Movie_Booking.services.MovieServices;

@Controller
public class MovieController {
    private MovieServices movieServices;
    @Autowired
    MovieController (MovieServices movieServices){
        this.movieServices=movieServices;
    }

    public RegisterMovieResponseDto registerMovie(RegisterMovieRequestDto requestDto){
        return movieServices.registerMovie(requestDto);
    }
}
