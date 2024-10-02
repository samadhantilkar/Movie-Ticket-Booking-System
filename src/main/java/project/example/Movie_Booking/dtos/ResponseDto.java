package project.example.Movie_Booking.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    @NonNull
    private ResponseDtoStatus status;
}
