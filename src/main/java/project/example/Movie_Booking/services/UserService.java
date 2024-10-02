package project.example.Movie_Booking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.exceptions.InvalidPasswordException;
import project.example.Movie_Booking.models.User;
import project.example.Movie_Booking.repositories.UserRepository;
import project.example.Movie_Booking.services.PasswordEncoder.PasswordEncoder;

import java.util.Optional;

@Service
@Qualifier("userService")
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static ModelMapper modelMapper=new ModelMapper();

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public RegisterUserResponseDto createUser(RegisterUserRequestDto request){
        User user=modelMapper.map(request,User.class);
        user.setPassword(passwordEncoder.getEncodedPassword(request.getPassword()));
        User savedUser=userRepository.save(user);

        RegisterUserResponseDto responseDto=new RegisterUserResponseDto();
        responseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return responseDto;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public UpdateUserResponseDto updateUser(UpdateUserRequestDto userRequestDto)throws Exception{
        User user = userRepository.findByEmail(userRequestDto.getEmail()).orElseThrow(()
                -> new UsernameNotFoundException("Invalid Email"));

        if(!passwordEncoder.matches(userRequestDto.getOldPassword(), user.getPassword())){
            throw new InvalidPasswordException("Wrong Password");
        }
        user.setPassword(userRequestDto.getNewPassword());
        userRepository.save(user);

        UpdateUserResponseDto updateUserResponseDto=new UpdateUserResponseDto();
        updateUserResponseDto.setStatus(ResponseDtoStatus.SUCCESS);
        return updateUserResponseDto;
    }
}