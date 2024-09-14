package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.exceptions.InvalidCityId;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.models.Auditorium;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.models.Theatre;
import project.example.Movie_Booking.repositories.AuditoriumRepository;
import project.example.Movie_Booking.repositories.TheatreRepository;

import java.util.Optional;
@Service
public class AuditoriumService {

    AuditoriumRepository auditoriumRepository;
    TheatreRepository theatreRepository;
    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository,
                             TheatreRepository theatreRepository){
        this.auditoriumRepository=auditoriumRepository;
        this.theatreRepository=theatreRepository;
    }

    public Auditorium addAuditorium(String name,int capacity,
                                    Long theatreId) throws TheatreNotFound {
//        Fetch The Theatre
        Optional<Theatre> optionalTheatre=theatreRepository.findById(theatreId);
        if(!optionalTheatre.isPresent()){
            throw new TheatreNotFound("Invlide Theatre Id");
        }
//        Create Auditorium
        Auditorium auditorium=new Auditorium();
        auditorium.setName(name);
        auditorium.setCapacity(capacity);
        auditorium.setTheatre(optionalTheatre.get());

//        Save Auditorium
        Auditorium savedAuditorium=auditoriumRepository.save(auditorium);

        Theatre dbTheatre=optionalTheatre.get();

//        Save The Auditorium in Theatre
        dbTheatre.getAuditoriums().add(savedAuditorium);

//        Save in db
        this.theatreRepository.save(dbTheatre);
        return savedAuditorium;
    }
}
