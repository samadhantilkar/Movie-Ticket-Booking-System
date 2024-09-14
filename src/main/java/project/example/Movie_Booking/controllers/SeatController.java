package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public List<Seat> createSeats(Long auditoriumId,
                                  Map<SeatType,Integer> seatCount) throws Exception
    {
        return seatService.createSeats(auditoriumId,seatCount);
    }
}
