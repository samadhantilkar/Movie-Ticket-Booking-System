package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.CreateShowRequestDto;
import project.example.Movie_Booking.dtos.CreateShowResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.*;

import java.util.*;

@Service
public class ShowService {
    private ShowRepository showRepository;
    private AuditoriumRepository auditoriumRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;
    private MovieRepository movieRepository;
    @Autowired
    ShowService(ShowRepository showRepository, AuditoriumRepository auditoriumRepository,
                ShowSeatRepository showSeatRepository, ShowSeatTypeRepository showSeatTypeRepository,
                MovieRepository movieRepository){
        this.showRepository=showRepository;
        this.auditoriumRepository=auditoriumRepository;
        this.showSeatRepository=showSeatRepository;
        this.showSeatTypeRepository=showSeatTypeRepository;
        this.movieRepository=movieRepository;
    }
    public CreateShowResponseDto createShow(CreateShowRequestDto requestDto)
    {
        Show show=new Show();
        show.setStartTime(requestDto.getStartTime());
        show.setEndTime(requestDto.getEndTime());
        show.setLanguage(requestDto.getLanguage());
        show.setShowFeatures(requestDto.getShowFeatures());

        Optional<Movie> optionalMovie=movieRepository.findById(requestDto.getMovieId());
        Movie movie=optionalMovie.get();
        show.setMovie(movie);

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
        List<ShowSeatType> showSeatTypes=new ArrayList<>();
        for(Map.Entry<SeatType,Integer> entry:requestDto.getShowSeatPrice().entrySet()){
            ShowSeatType showSeatType=new ShowSeatType();
            showSeatType.setSeatType(entry.getKey());
            showSeatType.setPrice(entry.getValue());
            showSeatType.setShow(savedShow);
            showSeatTypes.add(this.showSeatTypeRepository.save(showSeatType));
        }
        savedShow.setShowSeatTypes(showSeatTypes);
        showRepository.save(savedShow);




        CreateShowResponseDto responseDto=new CreateShowResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
