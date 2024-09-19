package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterAuditoriumRequestDto {
    private String name;
    private int capacity;
    private Long theatreId;
}
