package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BookTicketRequestDto {
    Long showId;
    List<Long> showSeatIds;
    Long userId;
}
