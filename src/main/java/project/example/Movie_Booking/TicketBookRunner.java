package project.example.Movie_Booking;

import project.example.Movie_Booking.controllers.PaymentController;
import project.example.Movie_Booking.controllers.TicketController;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.models.PaymentMethod;
import project.example.Movie_Booking.models.ShowSeat;
import project.example.Movie_Booking.services.Adapter.PaymentServices;
import project.example.Movie_Booking.services.TicketService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TicketBookRunner implements Runnable{

    private TicketController ticketController;
    private BookTicketRequestDto requestDto;
    private static Scanner scanner=new Scanner(System.in);
    private final PaymentController paymentController;

    public TicketBookRunner(TicketController ticketController, PaymentController paymentController,
                            BookTicketRequestDto requestDto){
        this.ticketController=ticketController;
        this.requestDto=requestDto;
        this.paymentController=paymentController;
    }


    @Override
    public void run() {
          try{
              BookTicketResponseDto bookTicketResponseDto=this.ticketController.bookTicket(requestDto);
              if(bookTicketResponseDto.getStatus().equals(ResponseDtoStatus.PENDING)){
                  PaymentRequestDto paymentRequestDto=new PaymentRequestDto();
                  boolean isCorrect=true;
                  while(isCorrect){
                      System.out.print("Enter payment method (DEBIT_CARD, CREDIT_CARD,): ");
                      String input=scanner.nextLine().toUpperCase();
                      try {
                          PaymentMethod method = PaymentMethod.valueOf(input);
                          paymentRequestDto.setPaymentMethod(method);
                          isCorrect=false;
                      } catch (IllegalArgumentException e) {
                          // Handle invalid input
                          System.out.println("Invalid payment method. Please enter a valid method.");
                      }
                  }
                  paymentRequestDto.setDate(new Date());
                  paymentRequestDto.setAmount(bookTicketResponseDto.getAmount());
                  paymentRequestDto.setShowId(requestDto.getShowId());
                  System.out.print("Enter Card Number:");
                  paymentRequestDto.setCardNumber(scanner.nextLine());
                  System.out.print("Enter CVV:");
                  paymentRequestDto.setCvv(scanner.nextInt());
                  PaymentResponseDto paymentResponseDto =paymentController.makePayment(paymentRequestDto);
                  if(paymentResponseDto.getStatus().equals(ResponseDtoStatus.SUCCESS)){
                      TicketResponseDto ticketResponseDto=ticketController.conformTicket(requestDto,paymentResponseDto);
                      System.out.println("Theatre Name:"+ticketResponseDto.getTheatreName());
                      System.out.println("Movie Name:"+ticketResponseDto.getMovieName());
                      System.out.println("Time:"+ticketResponseDto.getTime());
                      System.out.println("Amount:"+bookTicketResponseDto.getAmount());
                      System.out.print("Seats:");
                      for(ShowSeat showSeat:bookTicketResponseDto.getShowSeats()){
                          System.out.print(showSeat.getSeat().getSeatType().toString()+":"+showSeat.getSeat().getSeatNumber()+"  ");
                      }
                  }
              }
          }
          catch (Exception exception){
              System.out.println("EXCEPTIOOOOOOON:   " +exception.getMessage());
          }
    }
}
