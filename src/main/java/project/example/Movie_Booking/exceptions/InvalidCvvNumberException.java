package project.example.Movie_Booking.exceptions;

public class InvalidCvvNumberException extends Exception{
    public InvalidCvvNumberException(String message) {
        super(message);
    }
}
