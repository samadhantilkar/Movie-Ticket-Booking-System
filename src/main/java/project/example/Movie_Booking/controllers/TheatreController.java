package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.models.Theatre;
import project.example.Movie_Booking.services.TheatreService;

@Controller
public class TheatreController {

    private TheatreService theatreService;
    @Autowired
    public TheatreController(TheatreService theatreService){
        this.theatreService=theatreService;
    }

    public Theatre createTheatre(String name,
                                 String address,
                                 Long cityId ) throws TheatreNotFound {
        return this.theatreService.createTheatre(name,address,cityId);
    }
}
