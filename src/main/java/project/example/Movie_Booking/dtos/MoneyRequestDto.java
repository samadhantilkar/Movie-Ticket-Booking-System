package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoneyRequestDto{
    Double amount;
    List<Long> showSeatIds;
    Long Id;
}
