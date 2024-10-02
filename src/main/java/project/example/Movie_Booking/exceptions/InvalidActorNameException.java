package project.example.Movie_Booking.exceptions;

public class InvalidActorNameException extends Exception{
    public InvalidActorNameException(String message) {
        super(message);
    }
}