package project.example.Movie_Booking.dtos;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import project.example.Movie_Booking.models.Language;
import project.example.Movie_Booking.models.Movie;
import project.example.Movie_Booking.models.MovieFeature;

import java.util.List;

@Getter
@Setter
public class RegisterMovieRequestDto {
    private String name;
    private int length;
    private List<Language> languages;
    private List<Long> actorID;
    private List<MovieFeature> movieFeatures;
}
