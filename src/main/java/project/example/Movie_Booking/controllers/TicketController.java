package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.PaymentServices;
import project.example.Movie_Booking.services.TicketService;

import java.util.Date;
import java.util.Scanner;

@Controller
public class TicketController {

    private TicketService ticketService;
    private PaymentServices paymentServices;
    private static Scanner scanner=new Scanner(System.in);
    @Autowired
    public TicketController(TicketService ticketService,PaymentServices paymentServices){
        this.ticketService=ticketService;
        this.paymentServices=paymentServices;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception
    {
        BookTicketResponseDto bookTicketResponseDto=ticketService.bookTicket(bookTicketRequestDto);

        return bookTicketResponseDto;
    }

    public TicketResponseDto conformTicket(BookTicketRequestDto bookTicketRequestDto,PaymentResponseDto paymentResponseDto){
        return ticketService.conformTicket(bookTicketRequestDto,paymentResponseDto);
    }
}
