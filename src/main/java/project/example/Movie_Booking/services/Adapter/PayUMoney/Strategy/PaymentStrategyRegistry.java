package project.example.Movie_Booking.services.Adapter.PayUMoney.Strategy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;

import java.util.HashMap;
import java.util.Map;

@Component("razorpayStrategyRegistry")
public class PaymentStrategyRegistry {
    private Map<PaymentMethod, PayUPaymentStrategy> strategy=new HashMap<>();
    @Autowired
    PaymentStrategyRegistry(PayUCreditCardPayUPayment payUCreditCardPayment, PayUDebitCardPayUPayment payUDebitCardPayment){
        strategy.put(PaymentMethod.CREDIT_CARD, payUCreditCardPayment);
        strategy.put(PaymentMethod.DEBIT_CARD, payUDebitCardPayment);
    }

    public PayUPaymentStrategy get(PaymentMethod paymentMethod){
        return this.strategy.get(paymentMethod);
    }

    public void registerStrategy(PaymentMethod paymentMethod, PayUPaymentStrategy payUPaymentStrategy){
        this.strategy.put(paymentMethod, payUPaymentStrategy);
    }
}