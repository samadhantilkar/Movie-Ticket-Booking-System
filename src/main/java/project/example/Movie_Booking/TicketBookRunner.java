package project.example.Movie_Booking;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import project.example.Movie_Booking.controllers.PaymentController;
import project.example.Movie_Booking.controllers.SeatController;
import project.example.Movie_Booking.controllers.TicketController;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.models.ShowSeat;

import java.util.Date;
import java.util.Scanner;

public class TicketBookRunner implements Runnable {

    private final TicketController ticketController;
    private final PaymentController paymentController;
    private final BookTicketRequestDto requestDto;
    private final SeatController seatController;
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor initializes required controllers and request DTO
    public TicketBookRunner(TicketController ticketController, PaymentController paymentController,
                            BookTicketRequestDto requestDto,SeatController seatController) {
        this.ticketController = ticketController;
        this.seatController=seatController;
        this.paymentController = paymentController;
        this.requestDto = requestDto;
    }

    @Override
    public void run() {
        try {
            // Step 1: Book the ticket (pending status)
            BookTicketResponseDto bookTicketResponseDto = ticketController.bookTicket(requestDto);

            // Step 2: If the ticket is pending, proceed with payment
            if (bookTicketResponseDto.getStatus() .equals(ResponseDtoStatus.PENDING)) {
                PaymentRequestDto paymentRequestDto = createPaymentRequest(bookTicketResponseDto);

                // Step 3: Make the payment
                PaymentResponseDto paymentResponseDto = paymentController.makePayment(paymentRequestDto);

                // Step 4: If payment is successful, confirm the ticket
                if (paymentResponseDto.getStatus().equals(ResponseDtoStatus.SUCCESS)) {
                    TicketResponseDto ticketResponseDto = ticketController.confirmTicket(bookTicketResponseDto, paymentResponseDto);

                    // Step 5: If ticket confirmation is successful, print ticket details
                    if (ticketResponseDto.getStatus().equals(ResponseDtoStatus.SUCCESS)) {
                        printTicketDetails(ticketResponseDto, bookTicketResponseDto);
                    } else {
                        // If ticket confirmation fails, make the seats available again
                        seatController.makeSeatAvailable(bookTicketResponseDto);
                    }
                } else {
                    // If payment fails, make the seats available again
                    seatController.makeSeatAvailable(bookTicketResponseDto);
                }
            }
        } catch (Exception e) {
            // Catch any exceptions and print an error message
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    // Create a payment request by taking user inputs and ticket details
    private PaymentRequestDto createPaymentRequest(BookTicketResponseDto bookTicketResponseDto) {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setPaymentMethod(getValidPaymentMethod()); // Get valid payment method from user input
        paymentRequestDto.setDate(new Date()); // Set current date for payment
        paymentRequestDto.setAmount(bookTicketResponseDto.getAmount()); // Set amount from ticket response
        paymentRequestDto.setShowId(requestDto.getShowId()); // Set show ID from the ticket request
        paymentRequestDto.setCardNumber(getInput("Enter Card Number:")); // Get card number from user input
        paymentRequestDto.setCvv(getCvv()); // Get CVV from user input
        return paymentRequestDto;
    }

    // Prompt the user to enter a valid payment method
    private PaymentMethod getValidPaymentMethod() {
        while (true) {
            System.out.print("Enter payment method (DEBIT_CARD, CREDIT_CARD): ");
            String input = scanner.nextLine().toUpperCase(); // Convert input to uppercase for consistency
            try {
                return PaymentMethod.valueOf(input); // Try to convert the input into a valid enum
            } catch (IllegalArgumentException e) {
                // If the input is invalid, inform the user and ask again
                System.out.println("Invalid payment method. Please try again.");
            }
        }
    }

    // Get valid CVV from the user
    private int getCvv() {
        while (true) {
            try {
                System.out.print("Enter CVV: ");
                int cvv = scanner.nextInt(); // Read the CVV as an integer
                scanner.nextLine();  // Clear the buffer to prevent skipping next input
                return cvv;
            } catch (Exception e) {
                // Handle invalid input (e.g., non-integer)
                System.out.println("Invalid CVV. Please enter a valid number.");
                scanner.nextLine();  // Clear the invalid input
            }
        }
    }

    // Print ticket details (theatre name, movie name, time, seats, etc.)
    private void printTicketDetails(TicketResponseDto ticketResponseDto, BookTicketResponseDto bookTicketResponseDto) {
        System.out.println("Theatre Name: " + ticketResponseDto.getTheatreName());
        System.out.println("Movie Name: " + ticketResponseDto.getMovieName());
        System.out.println("Time: " + ticketResponseDto.getTime());
        System.out.println("Amount: " + bookTicketResponseDto.getAmount());
        System.out.print("Seats: ");

        // Loop through booked seats and display seat types and numbers
        for (ShowSeat showSeat : bookTicketResponseDto.getShowSeats()) {
            System.out.print(showSeat.getSeat().getSeatType() + ":" + showSeat.getSeat().getSeatNumber() + "  ");
        }
        System.out.println(); // New line after seat details
    }

    // Utility function to get input from the user with a custom prompt
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine(); // Return the input string
    }
}