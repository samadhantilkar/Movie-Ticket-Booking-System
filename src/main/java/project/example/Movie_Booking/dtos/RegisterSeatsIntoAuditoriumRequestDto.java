package project.example.Movie_Booking.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import project.example.Movie_Booking.models.SeatType;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSeatsIntoAuditoriumRequestDto {
    @NonNull
    @NotBlank
    private String cityName;

    @NonNull
    @NotBlank
    private String theatreName;

    @NonNull
    @NotBlank
    private String auditoriumName;

    @NonNull
    @NotBlank
    private Map<SeatType, Integer> seatCount;
}
