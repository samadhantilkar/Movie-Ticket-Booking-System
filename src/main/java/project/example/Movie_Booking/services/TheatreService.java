package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.models.Theatre;
import project.example.Movie_Booking.repositories.CityRepository;
import project.example.Movie_Booking.repositories.TheatreRepository;

import java.util.Optional;

@Service
public class TheatreService {

    private TheatreRepository theatreRepository;
    private CityRepository cityRepository;

    @Autowired
    public TheatreService(TheatreRepository theatreRepository,
                          CityRepository cityRepository){
        this.theatreRepository=theatreRepository;
        this.cityRepository=cityRepository;
    }

    public Theatre createTheatre(
            String name,
            String address,
            Long cityId
    ) throws TheatreNotFound {
//        Check If City With That ID Exists
        Optional<City> cityOptional=cityRepository.findById(cityId);

        if(!cityOptional.isPresent()){
            throw new TheatreNotFound("No City With Given Id");
        }
//        Create A Theatre Object
        Theatre theatre=new Theatre();
        theatre.setAddress(address);;
        theatre.setName(name);

//        save it in the database
        Theatre savedTheatre= theatreRepository.save(theatre);

//        Fetch The City for the id
        City dbCity=cityOptional.get();

//        Add the Theatre to that city
        dbCity.getTheatres().add(savedTheatre);

//        Update The City
        this.cityRepository.save(dbCity);
          return savedTheatre;
    }

}
