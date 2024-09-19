package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterAuditoriumResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
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

    public RegisterAuditoriumResponseDto addAuditorium(RegisterAuditoriumRequestDto requestDto) throws TheatreNotFound {
//        Fetch The Theatre
        Optional<Theatre> optionalTheatre=theatreRepository.findById(requestDto.getTheatreId());
        if(!optionalTheatre.isPresent()){
            throw new TheatreNotFound("Invlide Theatre Id");
        }
//        Create Auditorium
        Auditorium auditorium=new Auditorium();
        auditorium.setName(requestDto.getName());
        auditorium.setCapacity(requestDto.getCapacity());
        auditorium.setTheatre(optionalTheatre.get());

//        Save Auditorium
        Auditorium savedAuditorium=auditoriumRepository.save(auditorium);

        Theatre dbTheatre=optionalTheatre.get();

//        Save The Auditorium in Theatre
        dbTheatre.getAuditoriums().add(savedAuditorium);

//        Save in db
        this.theatreRepository.save(dbTheatre);
        RegisterAuditoriumResponseDto responseDto=new RegisterAuditoriumResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
