package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;

import org.springframework.stereotype.Component;
import project.example.Movie_Booking.services.Adapter.PaymentIds;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Component
public class CreditCardPayment implements PaymentStrategy {
    private PaymentIds paymentIds;
    private static Random random=new SecureRandom();

    @Override
    public Long payMoney(Double amount, Long cardNumber, Long cvv, Date expiryDate) {
        return generateNumber();
    }

    public Long generateNumber(){
        Long number;
        do{
            number=Math.abs(random.nextLong());
        }while (paymentIds.getGeneratedNumbers(number));
        paymentIds.setGeneratedNumbers(number);
        return number;
    }
}
