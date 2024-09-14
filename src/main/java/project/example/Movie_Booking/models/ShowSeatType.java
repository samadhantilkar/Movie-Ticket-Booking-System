package project.example.Movie_Booking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ShowSeatType extends BaseModel{
    //    1:1
//    M:1
    @ManyToOne
    private Show show;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private double price;
}
