package project.example.Movie_Booking;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.example.Movie_Booking.controllers.*;
import project.example.Movie_Booking.dtos.*;
import project.example.Movie_Booking.models.*;

import java.util.*;

@SpringBootApplication
@EnableJpaAuditing
public class MovieBookingApplication implements CommandLineRunner {

	private final UserController userController;
	private final CityController cityController;
	private final TheatreController theatreController;
	private final AuditoriumController auditoriumController;
	private final SeatController seatController;
	private final TicketController ticketController;
	private final ShowController showController;
	private final ActorController actorController;
	private final MovieController movieController;
	private final PaymentController paymentController;
	@Autowired
	public MovieBookingApplication(UserController userController, CityController cityController,
								   TheatreController theatreController, AuditoriumController auditoriumController,
								   SeatController seatController, TicketController ticketController,
								   ShowController showController, ActorController actorController,
								   MovieController movieController, PaymentController paymentController){
		this.paymentController=paymentController;
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
		userRequestDto.setEmail("samadhantilkar123@gmail.com");
		userRequestDto.setPassword("12345678");
		RegisterUserResponseDto responseDto=this.userController.registerUser(userRequestDto);
		System.out.println(responseDto.getStatus());
//
//		UpdateUserRequestDto updateUserRequestDto=new UpdateUserRequestDto();
//		updateUserRequestDto.setEmail("samadhantilkar123@gmail.com");
//		updateUserRequestDto.setOldPassword("123456789");
//		updateUserRequestDto.setNewPassword("samadhantilkar");
//		UpdateUserResponseDto updateUserResponseDto=this.userController.updateUser(updateUserRequestDto);
//		System.out.println(updateUserResponseDto.getStatus());

		RegisterCityRequestDto registerCityRequestDto=new RegisterCityRequestDto();
		registerCityRequestDto.setName("Nashik");
		RegisterCityResponseDto registerCityResponseDto=this.cityController.addCity(registerCityRequestDto);
		System.out.println(registerCityResponseDto.getStatus());


//		UpdateCityRequestDto updateCityRequestDto=new UpdateCityRequestDto();
//		updateCityRequestDto.setOldName("Nashik");
//		updateCityRequestDto.setNewName("Chalisgoan");
//		UpdateCityResponseDto updateCityResponseDto= cityController.updateCity(updateCityRequestDto);
//		System.out.println(updateCityResponseDto.getStatus());
//
		RegisterTheatreRequestDto registerTheatreRequestDto=new RegisterTheatreRequestDto();
		registerTheatreRequestDto.setName("PVR");
		registerTheatreRequestDto.setCity("Nashik");
		registerTheatreRequestDto.setAddress("City Central Mall Nashik");
		this.theatreController.registerTheatre(registerTheatreRequestDto);

		RegisterAuditoriumRequestDto registerAuditoriumRequestDto=new RegisterAuditoriumRequestDto();
		registerAuditoriumRequestDto.setCapacity(120);
		registerAuditoriumRequestDto.setCityName("Nashik");
		registerAuditoriumRequestDto.setTheatreName("PVR");
		registerAuditoriumRequestDto.setName("Hall");
		registerAuditoriumRequestDto.setAuditoriumFeatures(List.of(AuditoriumFeature.DOLBY,AuditoriumFeature.THREE_D));
		this.auditoriumController.createAuditorium(registerAuditoriumRequestDto);

		RegisterSeatsIntoAuditoriumRequestDto registerSeatsIntoAuditoriumRequestDto=new RegisterSeatsIntoAuditoriumRequestDto();
		Map<SeatType ,Integer> seatsForAudi=new LinkedHashMap<>();
		seatsForAudi.put(SeatType.VIP,20);
		seatsForAudi.put(SeatType.GOLD,100);
		registerSeatsIntoAuditoriumRequestDto.setSeatCount(seatsForAudi);
		registerSeatsIntoAuditoriumRequestDto.setCityName("Nashik");
		registerSeatsIntoAuditoriumRequestDto.setTheatreName("PVR");
		registerSeatsIntoAuditoriumRequestDto.setAuditoriumName("Hall");
		this.seatController.createSeats(registerSeatsIntoAuditoriumRequestDto);

		RegisterActorRequestDto registerActorRequestDto=new RegisterActorRequestDto();
		registerActorRequestDto.setName("Anushka Sharma");
		actorController.registerActor(registerActorRequestDto);
//
		RegisterMovieRequestDto registerMovieRequestDto=new RegisterMovieRequestDto();
		registerMovieRequestDto.setName("Sairat");
		registerMovieRequestDto.setLength(180);
		registerMovieRequestDto.setMovieFeatures(List.of(MovieFeature.THREE_D,MovieFeature.DOLBY));
		registerMovieRequestDto.setLanguages(List.of(Language.MARATHI));
		registerMovieRequestDto.setActorName(List.of("Anushka Sharma"));
		movieController.registerMovie(registerMovieRequestDto);

		CreateShowRequestDto showRequestDto=new CreateShowRequestDto();
		showRequestDto.setLanguage(Language.ENGLISH);
		showRequestDto.setShowFeatures(List.of(ShowFeature.TWO_D,ShowFeature.DOLBY));
		showRequestDto.setMovieName("Sairat");
		showRequestDto.setCityName("Nashik");
		showRequestDto.setTheatreName("PVR");
		showRequestDto.setAuditoriumName("Hall");
		showRequestDto.setStartTime(new Date());
		HashMap<SeatType,Integer> showSeatPrice=new LinkedHashMap<>();
		showSeatPrice.put(SeatType.GOLD,300);
		showSeatPrice.put(SeatType.VIP,500);
		showSeatPrice.put(SeatType.SILVER,200);
		showRequestDto.setShowSeatPrice(showSeatPrice);
		showRequestDto.setShowSeatType(List.of(1L,2L));
		System.out.println(this.showController.createShow(showRequestDto).getStatus());
//
		BookTicketRequestDto bookTicketRequestDto=new BookTicketRequestDto();
		bookTicketRequestDto.setShowId(1L);
		bookTicketRequestDto.setUserId(1L);
		bookTicketRequestDto.setShowSeatIds(List.of(34L,41L,11L));
		TicketBookRunner user1=new TicketBookRunner(this.ticketController,paymentController,bookTicketRequestDto,seatController);
//
		Thread t1=new Thread(user1);
//		Thread t2=new Thread(user2);
		t1.start();
//		t2.start();

//		PaymentRequestDto paymentRequestDto =new PaymentRequestDto();
//		paymentRequestDto.setShowId(1L);
//		paymentRequestDto.setAmount(1100D);
//		paymentRequestDto.setPaymentMethod(PaymentMethod.DEBIT_CARD);
//		paymentRequestDto.setCardNumber("321213124325");
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.YEAR, 5);
//		Date newDate = calendar.getTime();
//		paymentRequestDto.setDate(newDate);
//		paymentRequestDto.setCvv(4123);
//		PaymentResponseDto paymentResponseDto=paymentController.makePayment(paymentRequestDto);
//		System.out.println(paymentResponseDto.getStatus());
	}
}