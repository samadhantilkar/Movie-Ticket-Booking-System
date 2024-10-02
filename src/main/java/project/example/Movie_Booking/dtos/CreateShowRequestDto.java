package project.example.Movie_Booking.dtos;

import lombok.*;
import project.example.Movie_Booking.models.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor  // Replace AllArgsConstructor with RequiredArgsConstructor for @NonNull fields
public class CreateShowRequestDto {

    @NonNull
    private String movieName;

    @NonNull
    private Date startTime;
    @NonNull
    private String auditoriumName;

    @NonNull
    private String cityName;

    @NonNull
    private String theatreName;

    @NonNull
    private List<ShowFeature> showFeatures;
    @NonNull
    private Map<SeatType, Integer> showSeatPrice;
    @NonNull
    private Language language;
    @NonNull
    private List<Long> showSeatType;
}
