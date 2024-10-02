package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.dtos.RegisterTheatreRequestDto;
import project.example.Movie_Booking.dtos.RegisterTheatreResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.models.Theatre;
import project.example.Movie_Booking.repositories.CityRepository;
import project.example.Movie_Booking.repositories.TheatreRepository;

import java.util.List;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TheatreService(TheatreRepository theatreRepository, CityRepository cityRepository) {
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public RegisterTheatreResponseDto registerTheatre(RegisterTheatreRequestDto requestDto) throws TheatreNotFound {
        // Find the city and throw an exception if not found
        List<City> cityList = cityRepository.findByName(requestDto.getCity());

        if (cityList.isEmpty()) {
            throw new TheatreNotFound("No City With Given name");
        }

        // Save the theatre and update the city
        Theatre savedTheatre = saveTheatre(requestDto);
        updateCityWithTheatre(cityList.get(0), savedTheatre);

        // Return success response
        return createSuccessResponse();
    }

    // Save theatre using ModelMapper
    private Theatre saveTheatre(RegisterTheatreRequestDto requestDto) {
        Theatre theatre = modelMapper.map(requestDto, Theatre.class);
        return theatreRepository.save(theatre);
    }

    // Add the saved theatre to the city and update it
    private void updateCityWithTheatre(City city, Theatre theatre) {
        city.getTheatres().add(theatre);
        cityRepository.save(city);
    }

    // Create success response
    private RegisterTheatreResponseDto createSuccessResponse() {
        RegisterTheatreResponseDto responseDto = new RegisterTheatreResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}