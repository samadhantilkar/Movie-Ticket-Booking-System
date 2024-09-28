package project.example.Movie_Booking.dtos;

import lombok.Data;
import project.example.Movie_Booking.models.Seat;
import project.example.Movie_Booking.models.SeatType;
import project.example.Movie_Booking.models.ShowSeat;

import java.util.Date;
import java.util.List;

@Data
public class TicketResponseDto extends ResponseDto{
   private Double amount;
   private List<ShowSeat> showSeatTypes;
   private Date time;
   private String MovieName;
   private String theatreName;
}
