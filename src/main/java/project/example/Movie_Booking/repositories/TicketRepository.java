package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.example.Movie_Booking.models.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
