# Movie Ticket Booking Application

## Overview
The Movie Ticket Booking Application is a Java-based application developed using Spring Boot, designed to streamline the process of booking movie tickets. This application allows users to register, and book tickets while managing various aspects like cities, theatres, auditoriums, and seat selection.

## Features
- **User Registration & Authentication**: Secure user registration and login functionality.
- **City Management**: Ability to add and manage cities where theatres are located.
- **Theatre Management**: Register theatres and manage their details.
- **Auditorium Management**: Create auditoriums within theatres, specifying capacity and features.
- **Seat Management**: Create and categorize seats (e.g., VIP, Gold) within auditoriums.
- **Movie & Actor Registration**: Manage movie details and actor information.
- **Show Management**: Schedule shows for movies, including various features (e.g., Dolby, 3D).
- **Ticket Booking**: Book tickets for selected shows, handling concurrency with multithreading.
- **Payment Processing**: Integration with payment gateways (Razorpay, PayUMoney) using the Adapter design pattern.
- **Flexible Payment Modes**: Supports multiple payment options (credit card, debit card) using the Strategy design pattern.

## Technologies Used
- **Java**: Core programming language.
- **Spring Boot**: Framework for building the application.
- **JPA (Java Persistence API)**: For database interactions.
- **PostgreSQL**: Database management system.
- **Multithreading**: For handling concurrent ticket bookings.

## Design Patterns
- **Adapter Pattern**: Used for integrating payment gateways, allowing seamless payment processing.
- **Strategy Pattern**: Implemented for managing different payment modes (credit card and debit card).
