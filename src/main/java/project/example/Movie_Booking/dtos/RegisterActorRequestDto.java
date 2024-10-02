package project.example.Movie_Booking.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterActorRequestDto {
    @NonNull
    @NotBlank
    private String name;
}
