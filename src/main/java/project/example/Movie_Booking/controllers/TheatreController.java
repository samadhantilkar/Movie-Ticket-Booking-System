package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.InvalideTheatreNameException;
import project.example.Movie_Booking.exceptions.TheatreNotFound;
import project.example.Movie_Booking.services.TheatreService;

@Controller
public class TheatreController {

    private TheatreService theatreService;
    @Autowired
    public TheatreController(TheatreService theatreService){
        this.theatreService=theatreService;
    }

    public RegisterTheatreResponseDto registerTheatre(RegisterTheatreRequestDto requestDto) throws TheatreNotFound {
        try{
            validRegisterTheatreDetail(requestDto);
            return this.theatreService.registerTheatre(requestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            RegisterTheatreResponseDto registerTheatreResponseDto=new RegisterTheatreResponseDto();
            registerTheatreResponseDto.setStatus(ResponseDtoStatus.FAILURE);
            return registerTheatreResponseDto;
        }
    }

    private void validRegisterTheatreDetail(RegisterTheatreRequestDto requestDto)throws Exception{
        if(requestDto.getName().length()<3){
            throw new InvalideTheatreNameException("Name must be at least 3 characters long");
        }
    }
}
