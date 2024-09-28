package project.example.Movie_Booking.repositories;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import project.example.Movie_Booking.models.Show;
import project.example.Movie_Booking.models.ShowSeat;

import java.util.List;
@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name="javax.persistence.lock.timeout",value="100000")
    })
    List<ShowSeat> findByIdIn(List<Long> showSeatIds);
//        select * from show_seat
//        where show_seat_id in ()
//        for update;

    ShowSeat save(ShowSeat showSeat);
}
