package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.dtos.RegisterActorRequestDto;
import project.example.Movie_Booking.dtos.RegisterActorResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.Actor;
import project.example.Movie_Booking.repositories.ActorRepositories;

@Service
public class ActorServices {
    private final ActorRepositories actorRepositories;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorServices(ActorRepositories actorRepositories,ModelMapper modelMapper){
        this.modelMapper=modelMapper;
        this.actorRepositories=actorRepositories;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public RegisterActorResponseDto registerActor(RegisterActorRequestDto requestDto){
        Actor actor=modelMapper.map(requestDto,Actor.class);
        Actor savedActor=actorRepositories.save(actor);
        RegisterActorResponseDto responseDto=new RegisterActorResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}