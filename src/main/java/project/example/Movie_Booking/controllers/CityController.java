package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.InvalidCityNameException;
import project.example.Movie_Booking.services.CityService;

@Controller
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    public RegisterCityResponseDto addCity(RegisterCityRequestDto requestDto){
        try {
            validateAddCit(requestDto);
            RegisterCityResponseDto responseDto = this.cityService.addCity(requestDto);
            return responseDto;
        }catch (Exception e){
            System.out.println(e.getMessage());
            RegisterCityResponseDto responseDto=new RegisterCityResponseDto();
            responseDto.setStatus(ResponseDtoStatus.FAILURE);
            return responseDto;
        }
    }

    public UpdateCityResponseDto updateCity(UpdateCityRequestDto updateCityRequestDto){
        try {
            validateUpdateCit(updateCityRequestDto);
            return cityService.updateCity(updateCityRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            UpdateCityResponseDto updateCityResponseDto=new UpdateCityResponseDto();
            updateCityResponseDto.setStatus(ResponseDtoStatus.FAILURE);
            return updateCityResponseDto;
        }
    }

    private void validateAddCit(RegisterCityRequestDto requestDto)throws Exception{
        if(requestDto.getName()==(null)){
            throw new InvalidCityNameException("Name can not be null");
        }
        if (!requestDto.getName().matches("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$")) {
            throw new InvalidCityNameException("Name can not contain numbers or special characters.");
        }
    }

    private void validateUpdateCit(UpdateCityRequestDto requestDto)throws Exception{
        if(!requestDto.getNewName().matches("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$")){
            throw new InvalidCityNameException("Old Name can not contain numbers or special characters.");
        }
        if (!requestDto.getOldName().matches("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$")) {
            throw new InvalidCityNameException("New Name can not contain numbers or special characters.");
        }
    }
}
