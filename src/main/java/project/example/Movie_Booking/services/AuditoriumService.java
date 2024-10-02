package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.exceptions.InvalideTheatreNameException;
import project.example.Movie_Booking.dtos.RegisterAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterAuditoriumResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidCityNameException;
import project.example.Movie_Booking.models.Auditorium;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.models.Theatre;
import project.example.Movie_Booking.repositories.AuditoriumRepository;
import project.example.Movie_Booking.repositories.CityRepository;
import project.example.Movie_Booking.repositories.TheatreRepository;

import java.util.List;

@Service
public class AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;
    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;

    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository, TheatreRepository theatreRepository,
                             CityRepository cityRepository) {
        this.auditoriumRepository = auditoriumRepository;
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public RegisterAuditoriumResponseDto addAuditorium(RegisterAuditoriumRequestDto requestDto) throws Exception {

        City city = findCityByName(requestDto.getCityName());
        Theatre theatre = findTheatreInCity(city, requestDto.getTheatreName());

        Auditorium savedAuditorium = createAndSaveAuditorium(requestDto, theatre);

        addAuditoriumToTheatre(savedAuditorium, theatre);

        return createSuccessResponse();
    }

    private City findCityByName(String cityName) throws InvalidCityNameException {
        List<City> cityList = cityRepository.findByName(cityName);
        if (cityList.isEmpty()) {
            throw new InvalidCityNameException("City with name '" + cityName + "' not found");
        }
        return cityList.get(0);
    }

    private Theatre findTheatreInCity(City city, String theatreName) throws InvalideTheatreNameException {
        for(Theatre theatre:city.getTheatres()){
            if(theatre.getName().equals(theatreName)){
                return theatre;
            }
        }
        throw new InvalideTheatreNameException("Theatre with name '" + theatreName + "' not found in city '" + city.getName() + "'");
    }

    private Auditorium createAndSaveAuditorium(RegisterAuditoriumRequestDto requestDto, Theatre theatre) {
        Auditorium auditorium = new Auditorium();
        auditorium.setName(requestDto.getName());
        auditorium.setCapacity(requestDto.getCapacity());
        auditorium.setTheatre(theatre);
        auditorium.setAuditoriumFeatures(requestDto.getAuditoriumFeatures());

        return auditoriumRepository.save(auditorium);
    }

    private void addAuditoriumToTheatre(Auditorium auditorium, Theatre theatre) {
        theatre.getAuditoriums().add(auditorium);
        theatreRepository.save(theatre);
    }

    private RegisterAuditoriumResponseDto createSuccessResponse() {
        RegisterAuditoriumResponseDto responseDto = new RegisterAuditoriumResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
