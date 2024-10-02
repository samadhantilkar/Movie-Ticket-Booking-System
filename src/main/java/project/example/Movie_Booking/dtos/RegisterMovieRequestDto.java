package project.example.Movie_Booking.dtos;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import project.example.Movie_Booking.models.Language;
import project.example.Movie_Booking.models.Movie;
import project.example.Movie_Booking.models.MovieFeature;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMovieRequestDto {
    @NonNull
    private String name;

    private int length;

    @NonNull
    private List<Language> languages;

    @NonNull
    private List<String> actorName;

    @NonNull
    private List<MovieFeature> movieFeatures;
}
