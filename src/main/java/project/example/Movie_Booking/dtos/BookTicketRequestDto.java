package project.example.Movie_Booking.dtos;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor  // Replace AllArgsConstructor with RequiredArgsConstructor to handle @NonNull fields properly
public class BookTicketRequestDto {
    @NonNull
    private Long showId;
    private List<Long> showSeatIds;  // Add private access modifier
    @NonNull
    private Long userId;
}
