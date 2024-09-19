package project.example.Movie_Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.example.Movie_Booking.controllers.*;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.models.SeatType;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaAuditing
public class MovieBookingApplication implements CommandLineRunner {

	private UserController userController;
	private CityController cityController;
	private TheatreController theatreController;
	private AuditoriumController auditoriumController;
	private SeatController seatController;
	private TicketController ticketController;
	private ShowController showController;
	private ActorController actorController;
	private MovieController movieController;
	@Autowired
	public MovieBookingApplication(UserController userController,
								   CityController cityController,
								   TheatreController theatreController,
								   AuditoriumController auditoriumController,
								   SeatController seatController,
								   TicketController ticketController,
								   ShowController showController,
								   ActorController actorController,
								   MovieController movieController){
		this.movieController=movieController;
		this.userController=userController;
		this.cityController=cityController;
		this.theatreController=theatreController;
		this.auditoriumController=auditoriumController;
		this.seatController=seatController;
		this.ticketController=ticketController;
		this.showController=showController;
		this.actorController=actorController;
	}
	public static void main(String[] args) {

		SpringApplication.run(MovieBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RegisterUserRequestDto userRequestDto=new RegisterUserRequestDto();
		userRequestDto.setEmail("samadhantilkar@gmail.com");
		this.userController.registerUser(userRequestDto);

		RegisterCityRequestDto registerCityRequestDto=new RegisterCityRequestDto();
		registerCityRequestDto.setName("Nashik");
		this.cityController.addCity(registerCityRequestDto);


		RegisterTheatreRequestDto registerTheatreRequestDto=new RegisterTheatreRequestDto();
		registerTheatreRequestDto.setName("PVR");
		registerTheatreRequestDto.setCityId(1L);
		registerTheatreRequestDto.setAddress("City Central Mall Nashik");
		this.theatreController.registerTheatre(registerTheatreRequestDto);

		RegisterAuditoriumRequestDto registerAuditoriumRequestDto=new RegisterAuditoriumRequestDto();
		registerAuditoriumRequestDto.setCapacity(120);
		registerAuditoriumRequestDto.setTheatreId(1L);
		registerAuditoriumRequestDto.setName("Hall");
		this.auditoriumController.createAuditorium(registerAuditoriumRequestDto);

		Map<SeatType ,Integer> seatsForAudi=new HashMap<>();
		seatsForAudi.put(SeatType.VIP,20);
		seatsForAudi.put(SeatType.GOLD,100);
		this.seatController.createSeats(1L,seatsForAudi);

//		RegisterActorRequestDto registerActorRequestDto=new RegisterActorRequestDto();
//		registerActorRequestDto.setName("Anushaka Sharma");
//		actorController.registerActor(registerActorRequestDto);

//		RegisterMovieRequestDto registerMovieRequestDto=new RegisterMovieRequestDto();
//		registerMovieRequestDto.setName("Sairat");
//		registerMovieRequestDto.setLength(180);
//		registerMovieRequestDto.setMovieFeatures(List.of(MovieFeature.THREE_D,MovieFeature.DOLBY));
//		registerMovieRequestDto.setLanguages(List.of(Language.MARATHI));
//		registerMovieRequestDto.setActorID(List.of(1L));
//		movieController.registerMovie(registerMovieRequestDto);

//		CreateShowRequestDto showRequestDto=new CreateShowRequestDto();
//		showRequestDto.setLanguage(Language.ENGLISH);
//		showRequestDto.setMovieId(1L);
//		showRequestDto.setEndTime(new Date());
//		showRequestDto.setAudiId(1L);
//		showRequestDto.setStartTime(new Date());
//		HashMap<SeatType,Integer> showSeatPrice=new HashMap<>();
//		showSeatPrice.put(SeatType.GOLD,300);
//		showSeatPrice.put(SeatType.VIP,500);
//		showSeatPrice.put(SeatType.SILVER,200);
//		showRequestDto.setShowSeatPrice(showSeatPrice);
//		this.showController.createShow(showRequestDto);

//		BookTicketRequestDto bookTicketRequestDto=new BookTicketRequestDto();
//		bookTicketRequestDto.setShowId(1L);
//		bookTicketRequestDto.setUserId(1L);
//		bookTicketRequestDto.setShowSeatIds(List.of(1L,2L,3L));
//		bookTicketRequestDto.setShowSeatIds(List.of(3L,4L,5L));
//		TicketBookRunner user1=new TicketBookRunner(this.ticketController,bookTicketRequestDto);
//		TicketBookRunner user2=new TicketBookRunner(this.ticketController,1L,List.of(4L,5L,6L),1L);

//		Thread t1=new Thread(user1);
//		Thread t2=new Thread(user2);
//		t1.start();
//		t2.start();
	}
}