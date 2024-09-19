package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterSeatsIntoAuditoriumRequestDto;
import project.example.Movie_Booking.dtos.RegisterSeatsIntoAuditoriumResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidAuditoriumId;
import project.example.Movie_Booking.models.Auditorium;
import project.example.Movie_Booking.models.Seat;
import project.example.Movie_Booking.models.SeatType;
import project.example.Movie_Booking.repositories.AuditoriumRepository;
import project.example.Movie_Booking.repositories.SeatRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SeatService {

    AuditoriumRepository auditoriumRepository;
    SeatRepository seatRepository;
    @Autowired
    SeatService(AuditoriumRepository auditoriumRepository,
                SeatRepository seatRepository){
        this.auditoriumRepository=auditoriumRepository;
        this.seatRepository=seatRepository;
    }
    public RegisterSeatsIntoAuditoriumResponseDto createSeats(RegisterSeatsIntoAuditoriumRequestDto requestDto) throws Exception
    {
        Optional<Auditorium> optionalAuditorium=auditoriumRepository.findById(requestDto.getAuditoriumId());
        if(optionalAuditorium.isEmpty()){
            throw new InvalidAuditoriumId("Invalid Auditorium Id");
        }

        Auditorium auditorium= optionalAuditorium.get();

        List<Seat> seats=new ArrayList<>();

        for(Map.Entry<SeatType,Integer> entry :requestDto.getSeatCount().entrySet()){
            for(int i=0;i<entry.getValue();i++){
                Seat seat=new Seat();
                seat.setSeatType(entry.getKey());
                seat.setSeatNumber(entry.getKey().toString()+Integer.toString(i+1));
                seats.add(seat);
            }
        }

        List<Seat> savedSeats=seatRepository.saveAll(seats);

        auditorium.setSeats(savedSeats);
        auditoriumRepository.save(auditorium);

        RegisterSeatsIntoAuditoriumResponseDto responseDto=new RegisterSeatsIntoAuditoriumResponseDto();
        responseDto.setSeats(savedSeats);;
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
