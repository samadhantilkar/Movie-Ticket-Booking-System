package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.*;

@Component
public class RazorPayDebitCardRazorPayPayment implements RazorPayPaymentStrategy {
    @Autowired
    private RazorPayPaymentIds razorPayPaymentIds;
    private static final Random random = new SecureRandom();
    @Override
    public String payMoney(Double amount, Long cardNumber, Long cvv, Date date) {
        UUID uuid=UUID.randomUUID();
        return uuid.toString();
    }

}