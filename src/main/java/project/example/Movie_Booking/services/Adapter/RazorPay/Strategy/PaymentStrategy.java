package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;

import java.util.Date;

public interface PaymentStrategy {
    public Long payMoney(Double amount, Long cardNumber, Long cvv, Date expiryDate);
}
