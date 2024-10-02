package project.example.Movie_Booking.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateCityRequestDto {
    @NonNull
    private String oldName;
    @NonNull
    private String newName;
}
