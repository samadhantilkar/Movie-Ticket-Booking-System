package project.example.Movie_Booking.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; // Keep this
import org.springframework.transaction.annotation.Isolation;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.BookTicketRequestDto;
import project.example.Movie_Booking.dtos.BookTicketResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.ShowNotFound;
import project.example.Movie_Booking.exceptions.ShowSeatNotAvailableException;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.*;

import java.util.*;

@Service
public class TicketService {

    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final TicketReposity ticketReposity;
    @Autowired
    TicketService(ShowSeatRepository showSeatRepository, UserRepository userRepository,
                  ShowRepository showRepository,TicketReposity ticketReposity,
                  ShowSeatTypeRepository showSeatTypeRepository){
        this.showSeatRepository=showSeatRepository;
        this.userRepository=userRepository;
        this.showRepository=showRepository;
        this.ticketReposity=ticketReposity;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto) throws Exception
    {
//        Fetch given showSeat
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

        Optional<Show> show=showRepository.findById(requestDto.getShowId());
        if(show.isEmpty()){
            throw new ShowNotFound("Invalid Show Id");
        }

         List<ShowSeatType> showSeatTypes=show.get().getShowSeatTypes();
        HashMap<SeatType,Double> seatTypePrice=new HashMap<>();
        for(ShowSeatType seatType:showSeatTypes){
            seatTypePrice.put(seatType.getSeatType(),seatType.getPrice());
        }

        double amount=0;
        for(ShowSeat showSeat:showSeats){
            SeatType seatType=showSeat.getSeat().getSeatType();
            amount+= seatTypePrice.get(seatType);
        }

//        return the ticket object
        Ticket ticket=new Ticket();
        ticket.setTicketStatus(TicketStatus.PENDING);
        Optional<User> user=userRepository.findById(requestDto.getUserId());
        ticket.setBookedBy(user.get());
        ticket.setShow(show.get());
        ticket.setShowSeats(showSeats);
        ticket.setTotalAmount(amount);
        Ticket savedTicket=ticketReposity.save(ticket);

        BookTicketResponseDto bookTicketResponseDto=new BookTicketResponseDto();
        bookTicketResponseDto.setStatus(ResponseDtoStatus.PENDING);
        bookTicketResponseDto.setAmount(amount);
        bookTicketResponseDto.setUserID(requestDto.getUserId());
        return bookTicketResponseDto;
    }
}
