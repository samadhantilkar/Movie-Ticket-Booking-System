package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterTheatreRequestDto;
import project.example.Movie_Booking.dtos.RegisterTheatreResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
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

    public RegisterTheatreResponseDto registerTheatre(RegisterTheatreRequestDto requestDto) throws TheatreNotFound {
//        Check If City With That ID Exists
        Optional<City> cityOptional=cityRepository.findById(requestDto.getCityId());

        if(!cityOptional.isPresent()){
            throw new TheatreNotFound("No City With Given Id");
        }
//        Create A Theatre Object
        Theatre theatre=new Theatre();
        theatre.setAddress(requestDto.getAddress());;
        theatre.setName(requestDto.getName());

//        save it in the database
        Theatre savedTheatre= theatreRepository.save(theatre);

//        Fetch The City for the id
        City dbCity=cityOptional.get();

//        Add the Theatre to that city
        dbCity.getTheatres().add(savedTheatre);

//        Update The City
        this.cityRepository.save(dbCity);

        RegisterTheatreResponseDto responseDto=new RegisterTheatreResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
          return responseDto;
    }

}
