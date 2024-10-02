package project.example.Movie_Booking.exceptions;

public class InvalidSeatCapacityException extends Exception{
    public InvalidSeatCapacityException(String message) {
        super(message);
    }
}
