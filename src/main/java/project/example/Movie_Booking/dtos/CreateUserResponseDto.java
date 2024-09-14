package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.User;
@Getter
@Setter
public class CreateUserResponseDto {
    private User user;
}
