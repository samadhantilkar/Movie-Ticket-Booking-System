package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.CreateShowRequestDto;
import project.example.Movie_Booking.dtos.CreateShowResponseDto;
import project.example.Movie_Booking.models.Language;
import project.example.Movie_Booking.models.SeatType;
import project.example.Movie_Booking.models.Show;
import project.example.Movie_Booking.services.ShowService;

import java.util.Date;
import java.util.Map;

@Controller
public class ShowController  {

    private ShowService showService;
    @Autowired
    ShowController(ShowService showService){
        this.showService=showService;
    }

    public CreateShowResponseDto createShow(CreateShowRequestDto requestDto)
    {
        return showService.createShow(requestDto);
    }
}
