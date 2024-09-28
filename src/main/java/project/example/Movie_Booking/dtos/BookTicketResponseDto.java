package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.ShowSeat;
import project.example.Movie_Booking.models.User;

import java.util.List;

@Getter
@Setter
public class BookTicketResponseDto extends ResponseDto{
    Long userID;
    Double amount;
    List<ShowSeat> showSeats;
}
