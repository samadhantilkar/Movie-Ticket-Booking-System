package project.example.Movie_Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.example.Movie_Booking.controllers.*;
import project.example.Movie_Booking.dtos.CreateUserRequestDto;
import project.example.Movie_Booking.models.Language;
import project.example.Movie_Booking.models.SeatType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MovieBookingApplication implements CommandLineRunner {

	private UserController userController;
	private CityController cityController;
	private TheatreController theatreController;
	private AuditoriumController auditoriumController;
	private SeatController seatController;
	private TicketController ticketController;
	private ShowController showController;
	@Autowired
	public MovieBookingApplication(UserController userController,
								   CityController cityController,
								   TheatreController theatreController,
								   AuditoriumController auditoriumController,
								   SeatController seatController,
								   TicketController ticketController,
								   ShowController showController){
		this.userController=userController;
		this.cityController=cityController;
		this.theatreController=theatreController;
		this.auditoriumController=auditoriumController;
		this.seatController=seatController;
		this.ticketController=ticketController;
		this.showController=showController;
	}
	public static void main(String[] args) {

		SpringApplication.run(MovieBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CreateUserRequestDto request=new CreateUserRequestDto();
		request.setEmail("samadhantilkar@gmail.com");

		this.userController.createUser(request);
		this.cityController.addCity("Nahsik");
		this.theatreController.createTheatre("PVR",
				"City Central Mall, Nashik",1L);
		this.auditoriumController.createAuditorium("Hall",
				50,1L);

		Map<SeatType ,Integer> seatsForAudi=new HashMap<>();
		seatsForAudi.put(SeatType.VIP,20);
		seatsForAudi.put(SeatType.GOLD,100);
		this.seatController.createSeats(1L,seatsForAudi);

		this.showController.createShow(0L,new Date(),new Date(),
				1L,null, Language.ENGLISH);

		TicketBookRunner user1=new TicketBookRunner(this.ticketController,1L,List.of(1L,2L,4L),1L);
		TicketBookRunner user2=new TicketBookRunner(this.ticketController,1L,List.of(4L,5L,6L),1L);

		Thread t1=new Thread(user1);
		Thread t2=new Thread(user2);
		t1.start();
		t2.start();
	}
}