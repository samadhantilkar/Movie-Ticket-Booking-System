package project.example.Movie_Booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDto extends ResponseDto{
    @NonNull
    Double amount;
    @NonNull
    String id;
}
