package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterSeatsIntoAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterSeatsIntoAuditoriumResponseDto;
import project.example.Movie_Booking.models.Seat;
import project.example.Movie_Booking.models.SeatType;
import project.example.Movie_Booking.services.SeatService;

import java.util.List;
import java.util.Map;

@Controller
public class SeatController {

    SeatService seatService;
    @Autowired
    public SeatController(SeatService service){
        this.seatService=service;
    }
//    Map<SeatType,Integer> seatTypePrice
    public RegisterSeatsIntoAuditoriumResponseDto createSeats(RegisterSeatsIntoAuditoriumRequestDto requestDto) throws Exception
    {
        return seatService.createSeats(requestDto);
    }
}
