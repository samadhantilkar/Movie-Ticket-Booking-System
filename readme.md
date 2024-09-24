# Movie Booking System

## Description
This is a Spring Boot-based Movie Booking System that allows users to view movies, select shows, choose seats, and book tickets. The system provides a web-based interface for users to interact with the booking process.

## Features
- View list of available movies
- Select movie showtimes
- Choose seats for a selected show
- Book tickets for selected seats

## Technologies Used
- Java 11
- Spring Boot 2.x
- Thymeleaf (for server-side rendering)
- Maven (for dependency management)
- HTML/CSS/JavaScript (for frontend)

## Project Structure
```
Movie_Booking/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── project/
│   │   │       └── example/
│   │   │           └── Movie_Booking/
│   │   │               ├── controllers/
│   │   │               ├── dtos/
│   │   │               ├── models/
│   │   │               ├── repositories/
│   │   │               ├── services/
│   │   │               └── MovieBookingApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── main.css
│   │       │   └── js/
│   │       │       └── main.js
│   │       ├── templates/
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/
│       └── java/
├── pom.xml
└── README.md
```

## Setup and Installation
1. Ensure you have Java 11 and Maven installed on your system.
2. Clone the repository:
   ```
   git clone https://github.com/yourusername/Movie_Booking.git
   ```
3. Navigate to the project directory:
   ```
   cd Movie_Booking
   ```
4. Build the project:
   ```
   mvn clean install
   ```
5. Run the application:
   ```
   mvn spring-boot:run
   ```
6. Open a web browser and go to `http://localhost:8080` to access the application.

## API Endpoints
- GET `/api/movies`: Fetch all movies
- GET `/api/shows?movieId={id}`: Fetch shows for a specific movie
- GET `/api/seats?showId={id}`: Fetch seats for a specific show
- POST `/api/tickets/book`: Book tickets for selected seats

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is open source and available under the [MIT License](LICENSE).

## Contact
For any queries, please contact [MustafaPinjari] at [unlessuser99@gmail.com].
