package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.example.Movie_Booking.models.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
