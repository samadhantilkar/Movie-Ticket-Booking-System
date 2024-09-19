package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.Seat;

import java.util.List;

@Getter
@Setter
public class RegisterSeatsIntoAuditoriumResponseDto extends ResponseDto{
    private List<Seat> seats;
}
