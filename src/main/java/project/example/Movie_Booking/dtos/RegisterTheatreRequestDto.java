package project.example.Movie_Booking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTheatreRequestDto {
    @NonNull
    @NotBlank(message = "Theatre name cannot be blank")
    @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters")
    private String name;

    @NonNull
    @NotBlank(message = "Address cannot be blank")
    @Size(min = 10, max = 50, message = "Address must be between 10 and 50 characters")
    private String address;

    @NonNull
    private String city;
}
