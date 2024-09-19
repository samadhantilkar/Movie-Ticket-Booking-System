package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import project.example.Movie_Booking.dtos.BookTicketRequestDto;
import project.example.Movie_Booking.dtos.BookTicketResponseDto;
import project.example.Movie_Booking.models.Ticket;
import project.example.Movie_Booking.models.User;
import project.example.Movie_Booking.services.TicketService;

import java.util.List;
@Controller
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService=ticketService;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception
    {
        return ticketService.bookTicket(bookTicketRequestDto);
    }
}
