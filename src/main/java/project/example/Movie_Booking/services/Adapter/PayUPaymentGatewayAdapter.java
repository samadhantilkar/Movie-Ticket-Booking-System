package project.example.Movie_Booking.services.Adapter;

import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.services.Adapter.PayUMoney.PayUGateway;
import project.example.Movie_Booking.services.Adapter.PayUMoney.PayUPaymentStatus;
import project.example.Movie_Booking.services.Adapter.RazorPay.Strategy.PaymentStrategy;
import project.example.Movie_Booking.services.Adapter.RazorPay.Strategy.PaymentStrategyRegistry;

import java.util.Date;

public class PayUPaymentGatewayAdapter implements PaymentGateway{
    private PayUGateway payUGateway;

    public PayUPaymentGatewayAdapter(PayUGateway payUGateway){
        this.payUGateway=payUGateway;
    }

    @Override
    public Long payMoney(PaymentMethod paymentMethod, Double amount, String cardNumber, int cvv, Date date) {
        Long cardNumberLong=Long.parseLong(cardNumber);
        Long cvvLong=Long.parseLong(String.valueOf(cvv));
        String id=payUGateway.makeCCPayment(cardNumberLong,cvvLong,date);
        return Long.parseLong(id);
    }

    @Override
    public PaymentStatus getStatus(Long id) {
        PayUPaymentStatus payUPaymentStatus=payUGateway.checkPaymentStatus(id.toString());
        if(payUPaymentStatus.equals(PayUPaymentStatus.SUCCESS)){
            return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.FAILURE;
    }
}
