package project.example.Movie_Booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.services.SeatService;

@Controller
public class SeatController {

    private final SeatService seatService;


    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    public RegisterSeatsIntoAuditoriumResponseDto createSeats(RegisterSeatsIntoAuditoriumRequestDto requestDto) {
        try {
            validateCreateSeats(requestDto);
            return seatService.createSeats(requestDto);
        } catch (Exception e) {
            System.out.println("Error creating seats: {}"+ e.getMessage());
            RegisterSeatsIntoAuditoriumResponseDto responseDto = new RegisterSeatsIntoAuditoriumResponseDto();
            responseDto.setStatus(ResponseDtoStatus.FAILURE);
            return responseDto;
        }
    }

    public void makeSeatAvailable(BookTicketResponseDto bookTicketResponseDto){
        try {
            seatService.makeSeatAvailable(bookTicketResponseDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    // Validation logic for seat creation
    private void validateCreateSeats(RegisterSeatsIntoAuditoriumRequestDto requestDto) throws Exception {
        validateField(requestDto.getCityName(), "Empty City Name");
        validateField(requestDto.getTheatreName(), "Empty Theatre Name");
        validateField(requestDto.getAuditoriumName(), "Empty Auditorium Name");
    }


    // Utility method for field validation
    private void validateField(String field, String errorMessage) throws Exception {
        if (field.replaceAll("\\s", "").isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void ExceptionRollBack(BookTicketRequestDto bookTicketRequestDto){
        seatService.exceptionRollBack(bookTicketRequestDto);
    }
}
