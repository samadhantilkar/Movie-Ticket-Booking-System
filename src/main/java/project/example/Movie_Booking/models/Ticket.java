package project.example.Movie_Booking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@Entity
public class Ticket extends BaseModel{
//    1:1
//    M:1
    @ManyToOne
    private User bookedBy;

//    1:1
//    M:1
    @ManyToOne
    private Show show;

//    1:M
//    M:1
    @ManyToMany
    private List<ShowSeat>  showSeats;

    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    private Date timeOfBooking;
}
