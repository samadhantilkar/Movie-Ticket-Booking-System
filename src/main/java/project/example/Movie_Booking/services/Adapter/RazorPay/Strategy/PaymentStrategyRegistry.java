package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import java.util.HashMap;
@Component
public class PaymentStrategyRegistry {
    private HashMap<PaymentMethod,PaymentStrategy> strategy=new HashMap<>();
    @Autowired
    public PaymentStrategyRegistry(CreditCardPayment creditCardPayment, DebitCardPayment debitCardPayment) {
        strategy.put(PaymentMethod.CREDIT_CARD,creditCardPayment);
        strategy.put(PaymentMethod.DEBIT_CARD,debitCardPayment);
    }

    public void Register(PaymentMethod paymentMode,PaymentStrategy paymentStrategy){
        strategy.put(paymentMode,paymentStrategy);
    }

    public PaymentStrategy get(PaymentMethod paymentMode){
        return strategy.get(paymentMode);
    }
}