package project.example.Movie_Booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {
    private String email;
    private String newPassword;
    private String oldPassword;
}
