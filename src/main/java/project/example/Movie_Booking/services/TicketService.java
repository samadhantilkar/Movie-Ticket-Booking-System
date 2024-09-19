package project.example.Movie_Booking.services;
import org.springframework.transaction.annotation.Transactional; // Keep this
import org.springframework.transaction.annotation.Isolation;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.BookTicketRequestDto;
import project.example.Movie_Booking.dtos.BookTicketResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.ShowNotFound;
import project.example.Movie_Booking.exceptions.ShowSeatNotAvailableException;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.ShowRepository;
import project.example.Movie_Booking.repositories.ShowSeatRepository;
import project.example.Movie_Booking.repositories.TicketReposity;
import project.example.Movie_Booking.repositories.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class TicketService {

    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private TicketReposity ticketReposity;

//    @Autowired
    TicketService(ShowSeatRepository showSeatRepository, UserRepository userRepository,
                  ShowRepository showRepository,TicketReposity ticketReposity){
        this.showSeatRepository=showSeatRepository;
        this.userRepository=userRepository;
        this.showRepository=showRepository;
        this.ticketReposity=ticketReposity;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto) throws Exception
    {
//        Fetch given showseat
        List<ShowSeat> showSeats=showSeatRepository.findByIdIn(requestDto.getShowSeatIds());

//        Check if each of them available
        for(ShowSeat showSeat:showSeats){
            if(showSeat.getShowSeatState() != ShowSeatState.AVAILABLE){
                throw new ShowSeatNotAvailableException("ShowSeat Id:"+showSeat.getId().toString()+" not Available");
            }
        }

//        update status to locked

        for(ShowSeat showSeat:showSeats) {
            showSeat.setShowSeatState(ShowSeatState.LOCKED);
            showSeatRepository.save(showSeat);
        }
//        return the ticket object
        Ticket ticket=new Ticket();
        ticket.setTicketStatus(TicketStatus.PENDING);

        Optional<User> user=userRepository.findById(requestDto.getUserId());
        ticket.setBookedBy(user.get());

        Optional<Show> show=showRepository.findById(requestDto.getShowId());
        if(show.isEmpty()){
            throw new ShowNotFound("Invalid Show Id");
        }
        ticket.setShow(show.get());
        ticket.setShowSeats(showSeats);
        Ticket savedTicket=ticketReposity.save(ticket);

        BookTicketResponseDto bookTicketResponseDto=new BookTicketResponseDto();
        bookTicketResponseDto.setStatus(ResponseDtoStatus.PENDING);
        return bookTicketResponseDto;
    }
}
