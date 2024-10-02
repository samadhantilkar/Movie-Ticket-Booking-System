package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.InvalidAuditoriumNameException;
import project.example.Movie_Booking.exceptions.InvalidCityNameException;
import project.example.Movie_Booking.exceptions.InvalidDateException;
import project.example.Movie_Booking.exceptions.InvalideTheatreNameException;
import project.example.Movie_Booking.services.ShowService;

import java.util.Date;

@Controller
public class ShowController  {

    private ShowService showService;
    @Autowired
    ShowController(ShowService showService){
        this.showService=showService;
    }

    public CreateShowResponseDto createShow(CreateShowRequestDto requestDto)
    {
        try {
            validateCreteShow(requestDto);
            return showService.createShow(requestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            CreateShowResponseDto responseDto=new CreateShowResponseDto();
            responseDto.setStatus(ResponseDtoStatus.FAILURE);
            return responseDto;
        }
    }

    private void validateCreteShow(CreateShowRequestDto requestDto) throws Exception{
        if(requestDto.getStartTime().before(new Date())){
            throw new InvalidDateException("Date Must Be Greater Than Todays Date");
        }
        if(requestDto.getCityName().replaceAll("//s","").isEmpty()){
            throw new InvalidCityNameException("Empty city name");
        }
        if(requestDto.getTheatreName().replaceAll("//s","").isEmpty()){
            throw new InvalideTheatreNameException("Empty Theatre Name");
        }
        if(requestDto.getAuditoriumName().replaceAll("//s","").isEmpty()){
            throw new InvalidAuditoriumNameException("Empty Auditorium name");
        }
    }
}