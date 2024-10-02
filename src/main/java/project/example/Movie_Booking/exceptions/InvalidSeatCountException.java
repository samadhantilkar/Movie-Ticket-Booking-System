package project.example.Movie_Booking.exceptions;

public class InvalidSeatCountException extends Exception{
    public InvalidSeatCountException(String message) {
        super(message);
    }
}
