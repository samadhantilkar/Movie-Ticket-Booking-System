package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    public Show createShow(Long movieId,
                           Date startTime,
                           Date endTime,
                           Long audiId,
                           Map<SeatType,Integer> showSeatPrice,
                           Language language)
    {
        return showService.createShow(movieId, startTime, endTime, audiId, showSeatPrice, language);
    }
}
