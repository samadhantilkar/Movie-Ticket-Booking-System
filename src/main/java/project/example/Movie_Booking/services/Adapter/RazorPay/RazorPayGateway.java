package project.example.Movie_Booking.services.Adapter.RazorPay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.RazorPay.Strategy.PaymentStrategy;
import project.example.Movie_Booking.services.Adapter.RazorPay.Strategy.PaymentStrategyRegistry;

import java.util.Date;

@Component
public class RazorPayGateway {

    private PaymentStrategyRegistry paymentStrategyRegistry;
    @Autowired
    public RazorPayGateway(PaymentStrategyRegistry paymentStrategyRegistry){
        this.paymentStrategyRegistry=paymentStrategyRegistry;
    }

    public Long payMoney(Double amount,PaymentMethod paymentMethod, String creditCardNumber, String CVV, Date date){
        PaymentStrategy paymentStrategy= paymentStrategyRegistry.get(paymentMethod);
        Long paymentId= paymentStrategy.payMoney(amount,Long.parseLong(creditCardNumber),Long.parseLong(CVV),date);
        while(paymentId==null){
            System.out.println("Wait");
        }
        System.out.println("Payment Done By razorpay:"+paymentId);
        return paymentId;
    }

    public boolean checkPaymentStatus(String id){
        return true;
    }


}
