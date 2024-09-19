package project.example.Movie_Booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.RegisterCityRequestDto;
import project.example.Movie_Booking.dtos.RegisterCityResponseDto;
import project.example.Movie_Booking.dtos.ResponseDtoStatus;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.repositories.CityRepository;

@Service
public class CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository){
        this.cityRepository=cityRepository;
    }

    public RegisterCityResponseDto addCity(RegisterCityRequestDto requestDto){
        City newCity=new City();
        newCity.setName(requestDto.getName());
        City SavedCity=this.cityRepository.save(newCity);  //Id will be different here

        RegisterCityResponseDto responseDto=new RegisterCityResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }
}
