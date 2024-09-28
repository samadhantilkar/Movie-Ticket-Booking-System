package project.example.Movie_Booking.services.Adapter;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Data
public class PaymentIds {
    private HashSet<Long> generatedNumbers = new HashSet<>();

    public boolean getGeneratedNumbers(Long number) {
        return generatedNumbers.contains(number);
    }

    public void setGeneratedNumbers(Long number) {
        this.generatedNumbers.add(number);
    }
}
