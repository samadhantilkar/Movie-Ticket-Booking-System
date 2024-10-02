package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.example.Movie_Booking.models.Theatre;
import project.example.Movie_Booking.services.TheatreService;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long>{

//    @Override
//    Optional<Theatre> findByNameAndAddress(String name,String address);

    Theatre save(Theatre theatre);

    List<Theatre> findByName(String name);

}
