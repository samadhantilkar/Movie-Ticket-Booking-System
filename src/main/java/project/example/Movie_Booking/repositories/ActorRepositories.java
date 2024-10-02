package project.example.Movie_Booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.models.Actor;

import java.util.List;

@Service
public interface ActorRepositories extends JpaRepository<Actor,Long> {

    List<Actor> findByName(String name);
}
