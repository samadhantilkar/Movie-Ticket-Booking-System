package project.example.Movie_Booking.services.Adapter.RazorPay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.RazorPay.Strategy.RazorPayPaymentStrategy;
import project.example.Movie_Booking.services.Adapter.RazorPay.Strategy.PaymentStrategyRegistry;

import java.util.Date;

@Component
public class RazorPayGateway{

    private PaymentStrategyRegistry paymentStrategyRegistry;
    @Autowired
    public RazorPayGateway(PaymentStrategyRegistry paymentStrategyRegistry){
        this.paymentStrategyRegistry=paymentStrategyRegistry;
    }

    public String payMoney(Double amount,PaymentMethod paymentMethod, String creditCardNumber, String CVV, Date date){
        RazorPayPaymentStrategy razorPayPaymentStrategy = paymentStrategyRegistry.get(paymentMethod);
        String paymentId= razorPayPaymentStrategy.payMoney(amount,Long.parseLong(creditCardNumber),Long.parseLong(CVV),date);

        System.out.println("Payment Done By razorpay:"+paymentId);
        return paymentId;
    }

    public boolean checkPaymentStatus(String id){
        return true;
    }
}