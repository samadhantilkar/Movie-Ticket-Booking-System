package project.example.Movie_Booking.services.Adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.RazorPay.RazorPayGateway;

import java.util.Date;

@Component
public class RazorpayPaymentGatewayAdapter implements PaymentGateway{
    private RazorPayGateway razorPayGateway;
    @Autowired
    public RazorpayPaymentGatewayAdapter(RazorPayGateway razorPayGateway) {
        this.razorPayGateway = razorPayGateway;
    }

    @Override
    public String payMoney(PaymentMethod paymentMethod, Double amount, String cardNumber, int cvv, Date date) {
        String cvvString=String.valueOf(cvv);
        String ans= razorPayGateway.payMoney(amount,paymentMethod,cardNumber,cvvString,date);
        return ans;
    }

    @Override
    public PaymentStatus getStatus(Long id) {
        boolean status= razorPayGateway.checkPaymentStatus(String.valueOf(id));
        if(status){
            return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.FAILURE;
    }
}
