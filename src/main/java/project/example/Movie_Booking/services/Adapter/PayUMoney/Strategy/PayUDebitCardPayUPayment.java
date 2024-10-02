package project.example.Movie_Booking.services.Adapter.PayUMoney.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class PayUDebitCardPayUPayment implements PayUPaymentStrategy {
    @Override
    public String payMoney(Double amount, Long cardNumber, int cvv, Date expiryDate) {
        UUID uuid=UUID.randomUUID();
        return uuid.toString();
    }
}
