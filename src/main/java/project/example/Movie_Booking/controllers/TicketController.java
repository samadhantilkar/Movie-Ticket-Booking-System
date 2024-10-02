package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.EmptyShowSeatIdsException;
import project.example.Movie_Booking.services.PaymentServices;
import project.example.Movie_Booking.services.SeatService;
import project.example.Movie_Booking.services.TicketService;

import java.util.Scanner;

@Controller
public class TicketController {

    private TicketService ticketService;
    private SeatService seatService;
    @Autowired
    public TicketController(TicketService ticketService,SeatService seatService){
        this.ticketService=ticketService;
        this.seatService=seatService;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception
    {
        try {
            validBookTicket(bookTicketRequestDto);
            return ticketService.bookTicket(bookTicketRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            BookTicketResponseDto bookTicketResponseDto=new BookTicketResponseDto();
            bookTicketResponseDto.setStatus(ResponseDtoStatus.FAILURE);
            return bookTicketResponseDto;
        }
    }

    private void validBookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception{
        if(bookTicketRequestDto.getShowSeatIds().isEmpty()){
            throw new EmptyShowSeatIdsException("Empty Show Seat Id");
        }
    }

    public TicketResponseDto confirmTicket(BookTicketRequestDto bookTicketRequestDto,PaymentResponseDto paymentResponseDto){
        try {
            return ticketService.confirmTicket(bookTicketRequestDto,paymentResponseDto);
        }catch (Exception e){
            seatService.makeSeatAvailable(bookTicketRequestDto);
            System.out.println(e.getMessage());
            TicketResponseDto ticketResponseDto=new TicketResponseDto();
            ticketResponseDto.setStatus(ResponseDtoStatus.FAILURE);
            return ticketResponseDto;
        }
    }

    public void makeSeatAvailable(BookTicketResponseDto bookTicketResponseDto){
        try {
            BookTicketRequestDto bookTicketRequestDto=new BookTicketRequestDto();
            bookTicketResponseDto.setShowSeats(bookTicketResponseDto.getShowSeats());
            seatService.makeSeatAvailable(bookTicketRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
