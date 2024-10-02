package project.example.Movie_Booking.services.PasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptEncoder implements PasswordEncoder{

    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Override
    public String getEncodedPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean matches(String realPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(realPassword,hashedPassword);
    }
}
