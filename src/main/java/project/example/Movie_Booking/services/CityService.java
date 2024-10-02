package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.InvalidCityNameException;
import project.example.Movie_Booking.models.City;
import project.example.Movie_Booking.repositories.CityRepository;

import java.util.List;

@Service
public class CityService {
    private CityRepository cityRepository;
    private static ModelMapper modelMapper=new ModelMapper();

    @Autowired
    public CityService(CityRepository cityRepository){
        this.cityRepository=cityRepository;
    }

    public RegisterCityResponseDto addCity(RegisterCityRequestDto requestDto){
        City newCity=modelMapper.map(requestDto,City.class);
        City SavedCity=this.cityRepository.save(newCity);

        RegisterCityResponseDto responseDto=new RegisterCityResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }

    public UpdateCityResponseDto updateCity(UpdateCityRequestDto updateCityRequestDto)throws Exception{
        List<City> savedCity=cityRepository.findByName(updateCityRequestDto.getOldName());
        if(savedCity.isEmpty()){
            throw new InvalidCityNameException("Invalid city name");
        }
        City city= savedCity.get(0);
        city.setName(updateCityRequestDto.getNewName());
        cityRepository.save(city);

        UpdateCityResponseDto updateCityResponseDto=new UpdateCityResponseDto();
        updateCityResponseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return updateCityResponseDto;
    }
}
