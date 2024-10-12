package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterMovieRequestDto;
import project.example.Movie_Booking.dtos.RegisterMovieResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidActorNameException;
import project.example.Movie_Booking.models.Actor;
import project.example.Movie_Booking.models.Movie;
import project.example.Movie_Booking.repositories.ActorRepositories;
import project.example.Movie_Booking.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServices {
    private MovieRepository movieRepository;
    private ActorRepositories actorRepositories;
    private ModelMapper modelMapper;
    @Autowired
    MovieServices(MovieRepository movieRepository,ActorRepositories actorRepositories,
                  ModelMapper modelMapper){
        this.movieRepository=movieRepository;
        this.actorRepositories=actorRepositories;
        this.modelMapper=modelMapper;
    }

    public RegisterMovieResponseDto registerMovie(RegisterMovieRequestDto requestDto) throws Exception{
//        Create Movie
        Movie movie=modelMapper.map(requestDto,Movie.class);
//        Find Actor By Ids
        List<Actor> actors=new ArrayList<>();
        for(String actorName: requestDto.getActorName()){
            List<Actor> actors1=(actorRepositories.findByName(actorName));
            if (actors1.isEmpty()){
                throw new InvalidActorNameException("Actor "+ actorName+ " Name Not Found");
            }
            actors.add(actors1.get(0));
        }
        movie.setActors(actors);

//        Save Movie
        Movie savedMovie=movieRepository.save(movie);

//        Create Response
        RegisterMovieResponseDto responseDto=new RegisterMovieResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
//        Return Response
        return responseDto;
    }


}
