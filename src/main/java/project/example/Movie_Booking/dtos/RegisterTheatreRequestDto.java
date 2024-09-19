package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterTheatreRequestDto {
    private String name;
    private String address;
    private Long cityId;
}
