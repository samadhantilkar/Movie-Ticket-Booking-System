package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.repositories.CityRepository;

@Service
public class CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository){
        this.cityRepository=cityRepository;
    }

    public City addCity(String name){
        City newCity=new City();
        newCity.setName(name);
        return this.cityRepository.save(newCity);  //Id will be different here
    }
}
