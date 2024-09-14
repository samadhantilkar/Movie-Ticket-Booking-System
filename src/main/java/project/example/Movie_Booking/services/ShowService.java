package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.AuditoriumRepository;
import project.example.Movie_Booking.repositories.ShowRepository;
import project.example.Movie_Booking.repositories.ShowSeatRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {
    private ShowRepository showRepository;
    private AuditoriumRepository auditoriumRepository;
    private ShowSeatRepository showSeatRepository;
    @Autowired
    ShowService(ShowRepository showRepository,
                AuditoriumRepository auditoriumRepository,
                ShowSeatRepository showSeatRepository){
        this.showRepository=showRepository;
        this.auditoriumRepository=auditoriumRepository;
        this.showSeatRepository=showSeatRepository;
    }
    public Show createShow(Long movieId, Date startTime,
                           Date endTime, Long audiId,
                           Map<SeatType,Integer> showSeatPrice, Language language)
    {
        Show show=new Show();
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setLanguage(language);

        Auditorium auditorium=auditoriumRepository.findById(audiId).get();
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

        return showRepository.save(savedShow);
    }
}
