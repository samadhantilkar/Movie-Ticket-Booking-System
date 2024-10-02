package project.example.Movie_Booking.services.Adapter.RazorPay.Strategy;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Data
public class RazorPayPaymentIds {
    private HashSet<Long> generatedNumbers = new HashSet<>();

    public boolean getGeneratedNumbers(Long number) {
        return generatedNumbers.contains(number);
    }

    public void setGeneratedNumbers(Long number) {
        this.generatedNumbers.add(number);
    }
}
