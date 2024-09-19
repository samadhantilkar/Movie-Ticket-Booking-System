package project.example.Movie_Booking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private Date timeOfBooking;
}
