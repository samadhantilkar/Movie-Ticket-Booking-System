package project.example.Movie_Booking.dtos;

 import jakarta.validation.constraints.NotBlank;
 import lombok.*;
import project.example.Movie_Booking.models.Seat;
import project.example.Movie_Booking.models.SeatType;
import project.example.Movie_Booking.models.ShowSeat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto extends ResponseDto{
   @NonNull
   private Double amount;
   @NonNull
   private List<ShowSeat> showSeatTypes;

   @NonNull
   private Date time;

   @NotBlank(message = "Movie name cannot be blank")
   private String movieName;

   @NotBlank(message = "Theatre name cannot be blank")
   private String theatreName;

}
