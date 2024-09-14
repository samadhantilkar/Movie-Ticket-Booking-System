package project.example.Movie_Booking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
public class Payment extends BaseModel{

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Date timeOfPayment;

    private double amount;

    private String referenceId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
