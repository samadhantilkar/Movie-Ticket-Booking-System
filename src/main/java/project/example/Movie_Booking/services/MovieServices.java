package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterMovieRequestDto;
import project.example.Movie_Booking.dtos.RegisterMovieResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.Actor;
import project.example.Movie_Booking.models.Movie;
import project.example.Movie_Booking.repositories.ActorRepositories;
import project.example.Movie_Booking.repositories.MovieRepository;

import java.util.List;

@Service
public class MovieServices {
    private MovieRepository movieRepository;
    private ActorRepositories actorRepositories;
    @Autowired
    MovieServices(MovieRepository movieRepository,ActorRepositories actorRepositories){
        this.movieRepository=movieRepository;
        this.actorRepositories=actorRepositories;
    }

    public RegisterMovieResponseDto registerMovie(RegisterMovieRequestDto requestDto){
//        Create Movie
        Movie movie=new Movie();
        movie.setMovieFeatures(requestDto.getMovieFeatures());
        movie.setName(requestDto.getName());
        movie.setLength(requestDto.getLength());
//        Find Actor By Ids
        List<Actor> actors=actorRepositories.findAllById(requestDto.getActorID());
        movie.setActors(actors);
        movie.setLanguages(requestDto.getLanguages());
//        Save Movie
        Movie savedMovie=movieRepository.save(movie);

//        Create Response
        RegisterMovieResponseDto responseDto=new RegisterMovieResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
//        Return Response
        return responseDto;
    }


}
