package project.example.Movie_Booking.dtos;

import lombok.*;
import project.example.Movie_Booking.models.ShowSeat;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class BookTicketResponseDto extends ResponseDto {
    @NonNull
    private Long userID;
    @NonNull
    private Double amount;
    @NonNull
    private List<ShowSeat> showSeats;

    private Long ticketId;
}
