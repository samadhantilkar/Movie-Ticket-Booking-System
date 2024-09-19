package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import project.example.Movie_Booking.models.Language;
import project.example.Movie_Booking.models.SeatType;

import java.util.Date;
import java.util.Map;
@Getter
@Setter
public class CreateShowRequestDto {
    private Long movieId;
    private Date startTime;
    private Date endTime;
    private Long audiId;
    private Map<SeatType,Integer> showSeatPrice;
    private Language language;
}
