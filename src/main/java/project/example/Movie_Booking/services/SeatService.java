package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.dtos.BookTicketRequestDto;
import project.example.Movie_Booking.exceptions.InvalideTheatreNameException;
import project.example.Movie_Booking.dtos.RegisterSeatsIntoAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterSeatsIntoAuditoriumResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidCityNameException;
import project.example.Movie_Booking.exceptions.InvalidSeatCountException;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.AuditoriumRepository;
import project.example.Movie_Booking.repositories.CityRepository;
import project.example.Movie_Booking.repositories.SeatRepository;
import project.example.Movie_Booking.repositories.ShowSeatRepository;

import java.util.*;

@Service
public class SeatService {

    private final AuditoriumRepository auditoriumRepository;
    private final SeatRepository seatRepository;
    private final CityRepository cityRepository;
    private final ShowSeatRepository showSeatRepository;

    @Autowired
    public SeatService(AuditoriumRepository auditoriumRepository, SeatRepository seatRepository,
                       CityRepository cityRepository,ShowSeatRepository showSeatRepository) {
        this.auditoriumRepository = auditoriumRepository;
        this.seatRepository = seatRepository;
        this.cityRepository = cityRepository;
        this.showSeatRepository=showSeatRepository;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public RegisterSeatsIntoAuditoriumResponseDto createSeats(RegisterSeatsIntoAuditoriumRequestDto requestDto) throws Exception {
        // Validate the city name and fetch the city
        City city = validateCity(requestDto.getCityName());

        // Find the matching theatre and auditorium
        Auditorium auditorium = findAuditorium(city, requestDto.getTheatreName(), requestDto.getAuditoriumName());

        // Create and validate seats
        List<Seat> seats = createSeatsForAuditorium(requestDto, auditorium);

        // Save seats and update auditorium
        List<Seat> savedSeats = seatRepository.saveAll(seats);
        auditorium.setSeats(savedSeats);
        auditoriumRepository.save(auditorium);

        // Create and return the response
        return createSuccessResponse(savedSeats);
    }

    // Validate city name and retrieve the city
    private City validateCity(String cityName) throws InvalidCityNameException {
        List<City> cities = cityRepository.findByName(cityName);
        if (cities.isEmpty()) {
            throw new InvalidCityNameException("Invalid City Name: " + cityName);
        }
        return cities.get(0);
    }

    // Find the theatre and auditorium based on the names
    private Auditorium findAuditorium(City city, String theatreName, String auditoriumName) throws InvalideTheatreNameException {
        for (Theatre theatre : city.getTheatres()) {
            if (theatre.getName().equals(theatreName)) {
                for (Auditorium auditorium : theatre.getAuditoriums()) {
                    if (auditorium.getName().equals(auditoriumName)) {
                        return auditorium;
                    }
                }
            }
        }
        throw new InvalideTheatreNameException("Invalid Theatre or Auditorium Name");
    }

    // Create seats for the auditorium based on the seat count and types
    private List<Seat> createSeatsForAuditorium(RegisterSeatsIntoAuditoriumRequestDto requestDto, Auditorium auditorium) throws InvalidSeatCountException {
        List<Seat> seats = new ArrayList<>();
        int seatCount = 0;

        // Loop through seat types and create seats
        for (Map.Entry<SeatType, Integer> entry : requestDto.getSeatCount().entrySet()) {
            SeatType seatType = entry.getKey();
            int count = entry.getValue();
            seatCount += count;

            for (int i = 1; i <= count; i++) {
                Seat seat = new Seat();
                seat.setSeatType(seatType);
                seat.setSeatNumber(seatType.toString() + i);
                seats.add(seat);
            }
        }

        // Validate the seat count
        if (seatCount != auditorium.getCapacity()) {
            throw new InvalidSeatCountException("Seat count must match auditorium capacity: " + auditorium.getCapacity());
        }
        return seats;
    }

    // Create a success response
    private RegisterSeatsIntoAuditoriumResponseDto createSuccessResponse(List<Seat> savedSeats) {
        RegisterSeatsIntoAuditoriumResponseDto responseDto = new RegisterSeatsIntoAuditoriumResponseDto();
        responseDto.setSeats(savedSeats);
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void makeSeatAvailable(BookTicketRequestDto bookTicketRequestDto){
        List<ShowSeat> showSeats =showSeatRepository.findByIdIn(bookTicketRequestDto.getShowSeatIds());

        for(ShowSeat seats:showSeats){
            seats.setShowSeatState(ShowSeatState.AVAILABLE);
            showSeatRepository.save(seats);
        }
    }
}
