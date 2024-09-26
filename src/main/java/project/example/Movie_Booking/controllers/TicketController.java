package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.models.Ticket;
import project.example.Movie_Booking.models.User;
import project.example.Movie_Booking.services.PaymentServices;
import project.example.Movie_Booking.services.TicketService;

import java.util.List;
@Controller
public class TicketController {

    private TicketService ticketService;
    private final PaymentServices paymentServices;

    @Autowired
    public TicketController(TicketService ticketService,PaymentServices paymentServices){
        this.ticketService=ticketService;
        this.paymentServices=paymentServices;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception
    {
        BookTicketResponseDto bookTicketResponseDto=ticketService.bookTicket(bookTicketRequestDto);
        if(bookTicketResponseDto.getStatus().equals(ResponseDtoStatus.PENDING)){
            MoneyRequestDto moneyRequestDto=new MoneyRequestDto();
            moneyRequestDto.setShowSeatIds(bookTicketRequestDto.getShowSeatIds());
            moneyRequestDto.setId(bookTicketRequestDto.getUserId());
            moneyRequestDto.setAmount(bookTicketResponseDto.getAmount());
            MoneyResponseDto moneyResponseDto= paymentServices.makePayment(moneyRequestDto);
            if(moneyResponseDto.getStatus().equals(ResponseDtoStatus.SUCCESS)){
                return bookTicketResponseDto;
            }
        }
        bookTicketResponseDto.setStatus(ResponseDtoStatus.FAILURE);
        return bookTicketResponseDto;
    }
}
