package project.example.Movie_Booking.services.Adapter.PayUMoney.Strategy;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public interface PayUPaymentStrategy {
    public String payMoney(Double amount, Long cardNumber, int cvv, Date expiryDate);
}
