package project.example.Movie_Booking;

import project.example.Movie_Booking.controllers.TicketController;
import project.example.Movie_Booking.dtos.BookTicketRequestDto;
import project.example.Movie_Booking.services.TicketService;

import java.util.List;

public class TicketBookRunner implements Runnable{

    private TicketController ticketController;
    private BookTicketRequestDto requestDto;

    public TicketBookRunner(TicketController ticketController,
                            BookTicketRequestDto requestDto){
        this.ticketController=ticketController;
        this.requestDto=requestDto;
    }


    @Override
    public void run() {
          try{
                this.ticketController.bookTicket(requestDto);
          }
          catch (Exception exception){
              System.out.println("EXCEPTIOOOOOOON:   " +exception.getMessage());
          }
    }
}
