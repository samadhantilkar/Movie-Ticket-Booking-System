package project.example.Movie_Booking.dtos;

import lombok.*;
import project.example.Movie_Booking.models.Seat;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // Add this to allow initialization with seats
public class RegisterSeatsIntoAuditoriumResponseDto extends ResponseDto {
    @NonNull
    private List<Seat> seats;
}
