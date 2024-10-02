package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public interface RazorPayPaymentStrategy {
    public String payMoney(Double amount, Long cardNumber, Long cvv, Date expiryDate);
}
