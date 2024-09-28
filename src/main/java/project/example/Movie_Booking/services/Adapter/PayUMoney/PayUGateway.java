package project.example.Movie_Booking.services.Adapter.PayUMoney;

import project.example.Movie_Booking.services.Adapter.PaymentStatus;

import java.util.Date;

public class PayUGateway {
    public String makeCCPayment(Long creditCard, Long cvv, Date date){
        return "123";
    }
    public PayUPaymentStatus checkPaymentStatus(String id){
        return PayUPaymentStatus.SUCCESS;
    }
}
