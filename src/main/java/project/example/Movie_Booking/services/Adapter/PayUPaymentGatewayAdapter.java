package project.example.Movie_Booking.services.Adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.PayUMoney.PayUGateway;

import java.util.Date;
@Component
public class PayUPaymentGatewayAdapter implements PaymentGateway{
    private PayUGateway payUGateway;
    @Autowired()
    public PayUPaymentGatewayAdapter(PayUGateway payUGateway){
        this.payUGateway=payUGateway;
    }

    @Override
    public String payMoney(PaymentMethod paymentMethod, Double amount, String cardNumber, int cvv, Date date) {
        String stringcvv=String.valueOf(cvv);
        return payUGateway.payMoney(paymentMethod,amount,cardNumber,cvv,date);
    }

    @Override
    public PaymentStatus getStatus(Long id) {
        PaymentStatus payUPaymentStatus=payUGateway.getStatus(id);
        if(payUPaymentStatus.equals(PaymentStatus.SUCCESS)){
            return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.FAILURE;
    }
}
