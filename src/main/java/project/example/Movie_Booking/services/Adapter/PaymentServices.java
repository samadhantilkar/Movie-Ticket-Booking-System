package project.example.Movie_Booking.services.Adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.PaymentRequestDto;
import project.example.Movie_Booking.dtos.PaymentResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.Payment;
import project.example.Movie_Booking.models.PaymentStatus;
import project.example.Movie_Booking.repositories.PaymentRepository;

import java.util.Date;

@Service
public class PaymentServices {
    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServices(RazorpayPaymentGatewayAdapter razorpayPaymentGatewayAdapter,PaymentRepository paymentRepository) {
        this.paymentGateway=razorpayPaymentGatewayAdapter;
        this.paymentRepository=paymentRepository;
    }

    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {
        Long paymentId = paymentGateway.payMoney( paymentRequestDto.getPaymentMethod(),
                paymentRequestDto.getAmount(), paymentRequestDto.getCardNumber(),
                paymentRequestDto.getCvv(),paymentRequestDto.getDate());
        Payment payment=new Payment();
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setTimeOfPayment(new Date());
        payment.setReferenceId(String.valueOf(paymentId));
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        Payment savedPayment=paymentRepository.save(payment);

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(paymentId);
        paymentResponseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return paymentResponseDto;
    }
}