package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.example.Movie_Booking.models.Auditorium;
import project.example.Movie_Booking.models.Theatre;

import java.util.Optional;
@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium,Long> {
    @Override
    Auditorium save(Auditorium auditorium);

}
