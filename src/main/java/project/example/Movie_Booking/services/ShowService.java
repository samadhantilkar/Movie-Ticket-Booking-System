package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.*;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.*;

import java.util.*;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final CityRepository cityRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ShowService(ShowRepository showRepository, AuditoriumRepository auditoriumRepository,
                       ShowSeatRepository showSeatRepository, ShowSeatTypeRepository showSeatTypeRepository,
                       MovieRepository movieRepository, TheatreRepository theatreRepository,
                       ModelMapper modelMapper, CityRepository cityRepository) {
        this.showRepository = showRepository;
        this.cityRepository = cityRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.showSeatRepository = showSeatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.modelMapper = modelMapper;
    }

    public CreateShowResponseDto createShow(CreateShowRequestDto requestDto) throws Exception {
        // Map request DTO to Show model
        Show show = modelMapper.map(requestDto, Show.class);

        // Find and set the Movie

        Optional<Movie> movie = movieRepository.findByName(requestDto.getMovieName());
        if(movie.isEmpty()){
            throw new InvalidMovieNameException("Invalid Movie Name");
        }
        show.setMovie(movie.get());

        // Find the City and validate the Theatre and Auditorium
        Auditorium auditorium = findValidAuditorium(requestDto);

        show.setAuditorium(auditorium);

        // Save the Show
        Show savedShow = showRepository.save(show);

        // Link the Show to the Theatre and update upcoming shows
        linkShowToTheatre(auditorium.getTheatre(), savedShow);

        // Create ShowSeats and set them as available
        List<ShowSeat> savedShowSeats = createShowSeats(auditorium, savedShow);

        // Set pricing for ShowSeatTypes
        List<ShowSeatType> savedShowSeatTypes = setShowSeatPricing(requestDto, savedShow);

        // Update the Show with the saved seat and pricing information
        savedShow.setShowSeats(savedShowSeats);
        savedShow.setShowSeatTypes(savedShowSeatTypes);
        showRepository.save(savedShow);

        // Prepare and return the response
        return createSuccessResponse();
    }

    // Find and validate the Auditorium within the requested Theatre and City
    private Auditorium findValidAuditorium(CreateShowRequestDto requestDto) throws Exception {
        List<City> cities = cityRepository.findByName(requestDto.getCityName());
        if (cities.isEmpty()) {
            throw new InvalidCityNameException("Invalid City Name");
        }

        City city = cities.get(0);
        for (Theatre theatre : city.getTheatres()) {
            if (theatre.getName().equals(requestDto.getTheatreName())) {
                for (Auditorium auditorium : theatre.getAuditoriums()) {
                    if (auditorium.getName().equals(requestDto.getAuditoriumName())) {
                        return auditorium;
                    }
                }
                throw new InvalidAuditoriumNameException("Invalid Auditorium Name");
            }
        }
        throw new InvalideTheatreNameException("Invalid Theatre Name");
    }

    // Link the saved show to the theatre and update upcoming shows
    private void linkShowToTheatre(Theatre theatre, Show savedShow) {
        theatre.setUpcomingShows(List.of(savedShow));
        theatreRepository.save(theatre);
    }

    // Create ShowSeats and set them as available
    private List<ShowSeat> createShowSeats(Auditorium auditorium, Show savedShow) {
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (Seat seat : auditorium.getSeats()) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setShowSeatState(ShowSeatState.AVAILABLE);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
        return savedShowSeats;
    }

    // Set pricing for ShowSeatTypes based on the seat types and pricing info in the request
    private List<ShowSeatType> setShowSeatPricing(CreateShowRequestDto requestDto, Show savedShow) {
        List<ShowSeatType> showSeatTypes = new ArrayList<>();
        for (Map.Entry<SeatType, Integer> entry : requestDto.getShowSeatPrice().entrySet()) {
            ShowSeatType showSeatType = new ShowSeatType();
            showSeatType.setSeatType(entry.getKey());
            showSeatType.setPrice(entry.getValue());
            showSeatType.setShow(savedShow);
            showSeatTypes.add(showSeatTypeRepository.save(showSeatType));
        }
        return showSeatTypes;
    }

    // Create a success response DTO
    private CreateShowResponseDto createSuccessResponse() {
        CreateShowResponseDto responseDto = new CreateShowResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
