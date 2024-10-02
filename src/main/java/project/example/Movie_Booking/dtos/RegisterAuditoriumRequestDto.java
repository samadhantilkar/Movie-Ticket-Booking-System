package project.example.Movie_Booking.dtos;

import lombok.*;
import project.example.Movie_Booking.models.AuditoriumFeature;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAuditoriumRequestDto {
    @NonNull
    private String name;

    private int capacity;

    @NonNull
    private String theatreName;

    @NonNull
    private String cityName;

    private List<AuditoriumFeature> auditoriumFeatures;
}
