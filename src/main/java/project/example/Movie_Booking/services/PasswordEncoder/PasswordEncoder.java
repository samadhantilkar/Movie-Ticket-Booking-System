package project.example.Movie_Booking.services.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public interface PasswordEncoder {
    String getEncodedPassword(String password);
    boolean matches(String realPassword,String hashedPassword);
}
