package project.example.Movie_Booking.services;
import org.springframework.transaction.annotation.Transactional; // Keep this
import org.springframework.transaction.annotation.Isolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.exceptions.ShowNotFound;
import project.example.Movie_Booking.exceptions.ShowSeatNotAvailableException;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.ShowRepository;
import project.example.Movie_Booking.repositories.ShowSeatRepository;
import project.example.Movie_Booking.repositories.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class TicketService {

    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;

//    @Autowired
    TicketService(ShowSeatRepository showSeatRepository,
                  UserRepository userRepository,
                  ShowRepository showRepository){
        this.showSeatRepository=showSeatRepository;
        this.userRepository=userRepository;
        this.showRepository=showRepository;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Ticket bookTicket(Long showId,
                             List<Long> showSeatId,
                             Long userId) throws Exception
    {
//        Fetch given showseat
        List<ShowSeat> showSeats=showSeatRepository.findByIdIn(showSeatId);

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

        Optional<User> user=userRepository.findById(userId);
        
        ticket.setBookedBy(user.get());

        Optional<Show> show=showRepository.findById(showId);
        if(show.isEmpty()){
            throw new ShowNotFound("Invalid Show Id");
        }
        
        ticket.setShow(show.get());

        ticket.setShowSeats(showSeats);

        ticket.setTimeOfBooking(new Date());

        return ticket;
    }
}
