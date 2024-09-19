package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.SeatType;

import java.util.Map;
@Setter
@Getter
public class RegisterSeatsIntoAuditoriumRequestDto {
    private Long auditoriumId;
    private Map<SeatType,Integer> seatCount;
}
