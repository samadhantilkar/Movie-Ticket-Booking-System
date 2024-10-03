package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.*;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.*;

import java.util.*;

@Service
public class TicketService {

    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(ShowSeatRepository showSeatRepository, UserRepository userRepository,
                         ShowRepository showRepository, TicketRepository ticketRepository) {
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto) throws Exception {

        // Fetch and validate ShowSeats
        List<ShowSeat> showSeats = showSeatRepository.findByIdIn(requestDto.getShowSeatIds());
        if (showSeats.size() != requestDto.getShowSeatIds().size()) {
            throw new InvalidShowSeatIdException("Invalid Show Seat Ids: " + requestDto.getShowSeatIds());
        }

        // Check availability of each ShowSeat
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getShowSeatState() != ShowSeatState.AVAILABLE) {
                throw new ShowSeatNotAvailableException("ShowSeat Id: " + showSeat.getId() + " is not available.");
            }
        }

        // Lock the seats
        lockShowSeats(showSeats);

        // Fetch Show and calculate the total amount
        Show show = showRepository.findById(requestDto.getShowId())
                .orElseThrow(() -> new ShowNotFound("Invalid Show Id"));

        double totalAmount = calculateTotalAmount(showSeats, show.getShowSeatTypes());

        // Create and save the ticket
        Ticket ticket = createTicket(requestDto, show, showSeats, totalAmount);

        // Build response DTO
        return buildBookTicketResponse(requestDto, totalAmount, showSeats,ticket);
    }

    private void lockShowSeats(List<ShowSeat> showSeats) {
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatState(ShowSeatState.LOCKED);
            showSeatRepository.save(showSeat);
        }
    }

    private double calculateTotalAmount(List<ShowSeat> showSeats, List<ShowSeatType> showSeatTypes) {
        Map<SeatType, Double> seatTypePriceMap = new HashMap<>();
        for (ShowSeatType showSeatType : showSeatTypes) {
            seatTypePriceMap.put(showSeatType.getSeatType(), showSeatType.getPrice());
        }

        double totalAmount = 0;
        for (ShowSeat showSeat : showSeats) {
            SeatType seatType = showSeat.getSeat().getSeatType();
            totalAmount += seatTypePriceMap.get(seatType);
        }

        return totalAmount;
    }

    private Ticket createTicket(BookTicketRequestDto requestDto, Show show, List<ShowSeat> showSeats, double totalAmount) throws Exception {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.PENDING);
        ticket.setBookedBy(user);
        ticket.setShow(show);
        ticket.setShowSeats(showSeats);
        ticket.setTotalAmount(totalAmount);

        return ticketRepository.save(ticket);
    }

    private BookTicketResponseDto buildBookTicketResponse(BookTicketRequestDto requestDto, double amount, List<ShowSeat> showSeats,Ticket ticket) {
        BookTicketResponseDto responseDto = new BookTicketResponseDto();
        responseDto.setStatus(ResponseDtoStatus.PENDING);
        responseDto.setAmount(amount);
        responseDto.setUserID(requestDto.getUserId());
        responseDto.setShowSeats(showSeats);
        responseDto.setTicketId(ticket.getId());
        return responseDto;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public TicketResponseDto confirmTicket(BookTicketResponseDto bookTicketResponseDto, PaymentResponseDto paymentResponseDto) throws Exception{


        Optional<Ticket> ticket=ticketRepository.findById(bookTicketResponseDto.getTicketId()) ;
        if(ticket.isEmpty()){
            throw new InvalidTicketIdException("Invalid Ticket Id");
        }
        ticket.get().setTicketStatus(TicketStatus.SUCCESS);

        // Mark seats as booked
        ticket.get().setShowSeats(markSeatsAsBooked(bookTicketResponseDto.getShowSeats()));

        ticketRepository.save(ticket.get());

        // Build response DTO
        return buildTicketResponse(bookTicketResponseDto, paymentResponseDto);
    }

    private List<ShowSeat> markSeatsAsBooked(List<ShowSeat> showSeats) {
        List<ShowSeat> seats=new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatState(ShowSeatState.BOOKED);
            seats.add(showSeatRepository.save(showSeat));
        }
        return seats;
    }

    private TicketResponseDto buildTicketResponse(BookTicketResponseDto bookTicketResponseDto, PaymentResponseDto paymentResponseDto) throws Exception{
        Show show = bookTicketResponseDto.getShowSeats().get(0).getShow();

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setTicketId(bookTicketResponseDto.getTicketId());
        responseDto.setAmount(paymentResponseDto.getAmount());
        responseDto.setShowSeatTypes(bookTicketResponseDto.getShowSeats());
        responseDto.setMovieName(show.getMovie().getName());
        responseDto.setTime(show.getStartTime());
        responseDto.setTheatreName(show.getAuditorium().getTheatre().getName());
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }

    public void deleteTicket(TicketResponseDto ticketResponseDto){
        ticketRepository.deleteById(ticketResponseDto.getTicketId());
    }
}
