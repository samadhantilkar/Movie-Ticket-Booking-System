package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class CreateShowRequestDto {
    private Long movieId;
    private Date startTime;
    private Date endTime;
    private Long audiId;
    private List<ShowFeature> showFeatures;
    private Map<SeatType,Integer> showSeatPrice;
    private Language language;
    private List<Long> showSeatType;
}
