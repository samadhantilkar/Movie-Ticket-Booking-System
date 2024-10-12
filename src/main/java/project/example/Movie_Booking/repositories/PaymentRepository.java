package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.models.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByReferenceId(String referenceId);

    @Transactional
    int deleteByReferenceId(String referenceId);
}
