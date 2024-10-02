package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import java.util.HashMap;
@Component("payumoneyStrategyRegistry")
public class PaymentStrategyRegistry {
    private HashMap<PaymentMethod, RazorPayPaymentStrategy> strategy=new HashMap<>();
    @Autowired
    public PaymentStrategyRegistry(RazorPayCreditCardRazorPayPayment razorPayCreditCardPayment, RazorPayDebitCardRazorPayPayment razorPayDebitCardPayment) {
        strategy.put(PaymentMethod.CREDIT_CARD, razorPayCreditCardPayment);
        strategy.put(PaymentMethod.DEBIT_CARD, razorPayDebitCardPayment);
    }

    public void Register(PaymentMethod paymentMode, RazorPayPaymentStrategy razorPayPaymentStrategy){
        strategy.put(paymentMode, razorPayPaymentStrategy);
    }

    public RazorPayPaymentStrategy get(PaymentMethod paymentMode){
        return strategy.get(paymentMode);
    }
}