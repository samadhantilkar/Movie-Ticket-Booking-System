package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.services.CityService;

@Controller
public class CityController {

    private CityService cityService;
    @Autowired
    public CityController(CityService cityService){
        this.cityService=cityService;
    }

    public City addCity(String name){
        return this.cityService.addCity(name);
    }
}
