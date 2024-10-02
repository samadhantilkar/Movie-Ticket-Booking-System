package project.example.Movie_Booking.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCityRequestDto {
    @NonNull
    private String name;
}
