package project.example.Movie_Booking.services.Adapter;

import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;

import java.util.Date;

@Component
public interface PaymentGateway {
    Long payMoney(PaymentMethod paymentMethod, Double amount, String cardNumber, int cvv, Date Date);
    PaymentStatus getStatus(Long id);
}
