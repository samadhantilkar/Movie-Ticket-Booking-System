package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.RegisterActorRequestDto;
import project.example.Movie_Booking.dtos.RegisterActorResponseDto;
import project.example.Movie_Booking.services.ActorServices;

@Controller
public class ActorController {
    ActorServices actorServices;
    @Autowired
    ActorController(ActorServices actorServices){
        this.actorServices=actorServices;
    }

    public RegisterActorResponseDto registerActor(RegisterActorRequestDto requestDto){
        return actorServices.registerActor(requestDto);
    }
}
