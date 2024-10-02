package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.PaymentRequestDto;
import project.example.Movie_Booking.dtos.PaymentResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidCardNumberException;
import project.example.Movie_Booking.exceptions.InvalidCvvNumberException;
import project.example.Movie_Booking.services.PaymentServices;

@Controller
public class PaymentController {

    private final PaymentServices paymentServices;
    @Autowired
    PaymentController(PaymentServices paymentServices){
        this.paymentServices=paymentServices;
    }

    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto){
        try {
            validatePayment(paymentRequestDto);
            return paymentServices.makePayment(paymentRequestDto);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            PaymentResponseDto paymentResponseDto=new PaymentResponseDto();
            paymentResponseDto.setStatus(ResponseDtoStatus.FAILURE);
            return paymentResponseDto;
        }
    }

    private void validatePayment(PaymentRequestDto paymentRequestDto)throws Exception{
        if(paymentRequestDto.getCardNumber().length()!=12){
            throw new InvalidCardNumberException("Card Number must be 12 digit");
        }
        if(paymentRequestDto.getCvv()<=99){
            throw new InvalidCvvNumberException("CVV must be 3 digit");
        }
    }

}
