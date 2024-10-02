package project.example.Movie_Booking.services.Adapter.PayUMoney;

import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.PayUMoney.Strategy.PayUPaymentStrategy;
import project.example.Movie_Booking.services.Adapter.PayUMoney.Strategy.PaymentStrategyRegistry;
import project.example.Movie_Booking.services.Adapter.PaymentGateway;
import project.example.Movie_Booking.services.Adapter.PaymentStatus;

import java.util.Date;
@Component
public class PayUGateway{
    private PaymentStrategyRegistry paymentStrategyRegistry;

    PayUGateway(PaymentStrategyRegistry paymentStrategyRegistry){
        this.paymentStrategyRegistry=paymentStrategyRegistry;
    }
    public String payMoney(PaymentMethod paymentMethod, Double amount, String cardNumber, int cvv, Date date) {
        PayUPaymentStrategy payUPaymentStrategy =paymentStrategyRegistry.get(paymentMethod);
        String paymentId= payUPaymentStrategy.payMoney(amount,Long.parseLong(cardNumber),cvv,date);
        System.out.println("Payment Done By PayUMoney:"+paymentId);
        return  paymentId;
    }
    public PaymentStatus getStatus(Long id) {
        return null;
    }
}
