package project.example.Movie_Booking.services;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.MoneyRequestDto;
import project.example.Movie_Booking.dtos.MoneyResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.*;
import project.example.Movie_Booking.repositories.PaymentRepository;
import project.example.Movie_Booking.repositories.ShowSeatRepository;
import project.example.Movie_Booking.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class PaymentServices {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ShowSeatRepository showSeatRepository;
    @Autowired
    public PaymentServices(PaymentRepository paymentRepository,UserRepository userRepository,
                           ShowSeatRepository showSeatRepository){
        this.paymentRepository=paymentRepository;
        this.userRepository=userRepository;
        this.showSeatRepository=showSeatRepository;
    }

    public MoneyResponseDto makePayment(MoneyRequestDto moneyRequestDto){

        Optional<User> optionalUser=userRepository.findById(moneyRequestDto.getId());
        optionalUser.get().setBalance(optionalUser.get().getBalance()- moneyRequestDto.getAmount());
        userRepository.save(optionalUser.get());

        Payment payment=new Payment();
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTimeOfPayment(new Date());
        payment.setAmount(moneyRequestDto.getAmount());
        payment.setPaymentMethod(PaymentMethod.WALLET);
        paymentRepository.save(payment);

        List<ShowSeat> showSeats=showSeatRepository.findByIdIn(moneyRequestDto.getShowSeatIds());

        for(ShowSeat showSeat:showSeats){
            showSeat.setShowSeatState(ShowSeatState.BOOKED);
            showSeatRepository.save(showSeat);
        }

        MoneyResponseDto moneyResponseDto=new MoneyResponseDto();
        moneyResponseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return moneyResponseDto;
    }

}
