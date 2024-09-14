package project.example.Movie_Booking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ShowSeat extends BaseModel{
//    1:1
//    M:1
    @ManyToOne
    public Show show;

//    1:1
//    M:1
    @ManyToOne
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private ShowSeatState showSeatState;
}
