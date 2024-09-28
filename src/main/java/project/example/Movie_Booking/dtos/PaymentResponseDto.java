package project.example.Movie_Booking.dtos;

import lombok.Data;

@Data
public class PaymentResponseDto extends ResponseDto{
    Double amount;
    Long id;
}
