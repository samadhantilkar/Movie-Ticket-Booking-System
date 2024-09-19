package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.AuditoriumFeature;

import java.util.List;

@Setter
@Getter
public class RegisterAuditoriumRequestDto {
    private String name;
    private int capacity;
    private Long theatreId;
    private List<AuditoriumFeature> auditoriumFeatures;
}
