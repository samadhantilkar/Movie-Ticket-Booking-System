package project.example.Movie_Booking.dtos;

import lombok.Data;
import project.example.Movie_Booking.models.PaymentMethod;

import java.util.Date;

@Data
public class PaymentRequestDto {
    Long showId;
    Double amount;
    String cardNumber;
    int cvv;
    Date date;
    PaymentMethod paymentMethod;
}
