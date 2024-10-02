package project.example.Movie_Booking.exceptions;

public class InvalidCardNumberException extends Exception{
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
