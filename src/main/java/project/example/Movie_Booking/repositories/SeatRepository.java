package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.example.Movie_Booking.models.Seat;
@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {

    @Override
    Seat save(Seat seat);

}
