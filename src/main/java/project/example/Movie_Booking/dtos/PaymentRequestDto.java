package project.example.Movie_Booking.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;
import project.example.Movie_Booking.models.PaymentMethod;
import jakarta.validation.constraints.NotBlank; // Import NotBlank

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequestDto {
    @NonNull
    private Long showId;

    @NonNull
    private Double amount;

    @NotBlank (message = "card Number cannot be blank")
    @Size(min = 12,max = 12,message = "Card number must be exactly 12 digits")
    private String cardNumber;

    @NonNull
    private int cvv;

    @NonNull
    private Date date;

    @NonNull
    private PaymentMethod paymentMethod;
}
