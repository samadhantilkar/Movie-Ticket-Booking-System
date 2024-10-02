package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterActorRequestDto;
import project.example.Movie_Booking.dtos.RegisterActorResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.exceptions.InvalidActorNameException;
import project.example.Movie_Booking.services.ActorServices;

@Controller
public class ActorController {
    ActorServices actorServices;
    @Autowired
    ActorController(ActorServices actorServices){
        this.actorServices=actorServices;
    }

    public RegisterActorResponseDto registerActor(RegisterActorRequestDto requestDto){
        try {
            validate(requestDto);
            return actorServices.registerActor(requestDto);

        }catch (Exception e){
            System.out.println(e.getMessage());
            RegisterActorResponseDto responseDto=new RegisterActorResponseDto();
            responseDto.setStatus(ResponseDtoStatus.FAILURE);
            return responseDto;
        }
    }

    private void validate(RegisterActorRequestDto requestDto)throws Exception{
        if(requestDto.getName().replaceAll("//s","").isEmpty()){
            throw new InvalidActorNameException("Empty Actor Name");
        }
    }
}
