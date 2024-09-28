package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.services.Adapter.PaymentIds;

import java.security.SecureRandom;
import java.util.*;

@Component
public class DebitCardPayment implements PaymentStrategy{
    @Autowired
    private PaymentIds paymentIds;
    private static final Random random = new SecureRandom();
    @Override
    public Long payMoney(Double amount, Long cardNumber, Long cvv, Date date) {
        return generateUniqueNumber();
    }

    public long generateUniqueNumber() {
        long number=0L;
        do {
            // Generate a random number
            number = Math.abs(random.nextLong());
        } while (paymentIds.getGeneratedNumbers(number)); // Ensure the number is unique

        // Add the new unique number to the set
        paymentIds.setGeneratedNumbers(number);
        return number;
    }
}
