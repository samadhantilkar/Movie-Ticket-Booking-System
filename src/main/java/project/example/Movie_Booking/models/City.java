package project.example.Movie_Booking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Setter
@Getter
public class City extends BaseModel{
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Theatre> theatres=new ArrayList<>();
}
