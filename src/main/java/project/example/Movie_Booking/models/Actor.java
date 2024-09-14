package project.example.Movie_Booking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Actor extends BaseModel{
    private String name;

    @ManyToMany(mappedBy = "actors")
//    Actor : Movie
//    1     : M
//    M     : 1
//    M     :M
    private List<Movie> movies;
}
