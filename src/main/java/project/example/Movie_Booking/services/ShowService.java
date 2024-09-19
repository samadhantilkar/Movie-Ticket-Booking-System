package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.CreateShowRequestDto;
import project.example.Movie_Booking.dtos.CreateShowResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.AuditoriumRepository;
import project.example.Movie_Booking.repositories.ShowRepository;
import project.example.Movie_Booking.repositories.ShowSeatRepository;
import project.example.Movie_Booking.repositories.ShowSeatTypeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {
    private ShowRepository showRepository;
    private AuditoriumRepository auditoriumRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;
    @Autowired
    ShowService(ShowRepository showRepository, AuditoriumRepository auditoriumRepository,
                ShowSeatRepository showSeatRepository, ShowSeatTypeRepository showSeatTypeRepository){
        this.showRepository=showRepository;
        this.auditoriumRepository=auditoriumRepository;
        this.showSeatRepository=showSeatRepository;
        this.showSeatTypeRepository=showSeatTypeRepository;
    }
    public CreateShowResponseDto createShow(CreateShowRequestDto requestDto)
    {
        Show show=new Show();
        show.setStartTime(requestDto.getStartTime());
        show.setEndTime(requestDto.getEndTime());
        show.setLanguage(requestDto.getLanguage());

        Auditorium auditorium=auditoriumRepository.findById(requestDto.getAudiId()).get();
        show.setAuditorium(auditorium);

        Show savedShow=showRepository.save(show);
        List<ShowSeat> savedShowSeat=new ArrayList<>();
        for(Seat seat:auditorium.getSeats()){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setShowSeatState(ShowSeatState.AVAILABLE);
            savedShowSeat.add(showSeatRepository.save(showSeat));
        }

        savedShow.setShowSeats(savedShowSeat);

        for(ShowSeatType seatType: savedShow.getShowSeatTypes()){
            int price=requestDto.getShowSeatPrice().get(seatType.getSeatType());
            ShowSeatType showSeatType=new ShowSeatType();
            showSeatType.setSeatType(seatType.getSeatType());
            showSeatType.setShow(seatType.getShow());
            showSeatTypeRepository.save(showSeatType);
        }
        CreateShowResponseDto responseDto=new CreateShowResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
