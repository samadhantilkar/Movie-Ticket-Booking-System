package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.PaymentRequestDto;
import project.example.Movie_Booking.dtos.PaymentResponseDto;
import project.example.Movie_Booking.services.Adapter.PaymentServices;

@Controller
public class PaymentController {

    private final PaymentServices paymentServices;
    @Autowired
    PaymentController(PaymentServices paymentServices){
        this.paymentServices=paymentServices;
    }

    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto){
        return paymentServices.makePayment(paymentRequestDto);
    }

}
