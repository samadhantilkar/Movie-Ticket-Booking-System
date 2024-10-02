package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.dtos.PaymentRequestDto;
import project.example.Movie_Booking.dtos.PaymentResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.Payment;
import project.example.Movie_Booking.models.PaymentStatus;
import project.example.Movie_Booking.repositories.PaymentRepository;
import project.example.Movie_Booking.services.Adapter.PayUPaymentGatewayAdapter;
import project.example.Movie_Booking.services.Adapter.PaymentGateway;
import project.example.Movie_Booking.services.Adapter.RazorpayPaymentGatewayAdapter;

import java.util.Date;

@Service
public class PaymentServices {
    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentServices(PayUPaymentGatewayAdapter paymentGateway, PaymentRepository paymentRepository,
                           ModelMapper modelMapper) {
        this.paymentGateway=paymentGateway;
        this.paymentRepository=paymentRepository;
        this.modelMapper=modelMapper;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {
        String paymentId = paymentGateway.payMoney( paymentRequestDto.getPaymentMethod(),
                paymentRequestDto.getAmount(), paymentRequestDto.getCardNumber(),
              paymentRequestDto.getCvv(),paymentRequestDto.getDate());
        Payment payment=modelMapper.map(paymentRequestDto,Payment.class);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        Payment savedPayment=paymentRepository.save(payment);

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(paymentId);
        paymentResponseDto.setStatus(ResponseDtoStatus.SUCCESS);
        paymentResponseDto.setAmount(paymentRequestDto.getAmount());
        return paymentResponseDto;
    }
}