package project.example.Movie_Booking.exceptions;

public class InvalidTicketIdException extends Exception{
    public InvalidTicketIdException(String message) {
        super(message);
    }
}
