package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterActorRequestDto;
import project.example.Movie_Booking.dtos.RegisterActorResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.Actor;
import project.example.Movie_Booking.repositories.ActorRepositories;

@Service
public class ActorServices {
    private ActorRepositories actorRepositories;
    @Autowired
    public ActorServices(ActorRepositories actorRepositories){
        this.actorRepositories=actorRepositories;
    }

    public RegisterActorResponseDto registerActor(RegisterActorRequestDto requestDto){
        Actor actor=new Actor();
        actor.setName(requestDto.getName());
        Actor savedActor=actorRepositories.save(actor);

        RegisterActorResponseDto responseDto=new RegisterActorResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }

}
