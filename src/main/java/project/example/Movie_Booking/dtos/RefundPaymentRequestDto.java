package project.example.Movie_Booking.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundPaymentRequestDto {
    @NonNull
    String paymentId;
}
