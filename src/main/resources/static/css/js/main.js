// API endpoints
const API_BASE_URL = '/api';
const ENDPOINTS = {
    movies: `${API_BASE_URL}/movies`,
    shows: `${API_BASE_URL}/shows`,
    seats: `${API_BASE_URL}/seats`,
    bookTicket: `${API_BASE_URL}/tickets/book`
};

// DOM elements
const movieList = document.getElementById('movie-list');
const movieSelect = document.getElementById('movie-select');
const showSelect = document.getElementById('show-select');
const seatSelect = document.getElementById('seat-select');
const bookingForm = document.getElementById('booking-form');

// Fetch movies from the backend
async function fetchMovies() {
    try {
        const response = await fetch(ENDPOINTS.movies);
        return await response.json();
    } catch (error) {
        console.error('Error fetching movies:', error);
        return [];
    }
}

// Populate movie list
async function populateMovies() {
    const movies = await fetchMovies();
    movieList.innerHTML = '';
    movieSelect.innerHTML = '<option value="">Select a movie</option>';
    movies.forEach(movie => {
        const movieCard = document.createElement('div');
        movieCard.classList.add('movie-card');
        movieCard.innerHTML = `
            <h3>${movie.name}</h3>
            <p>Length: ${movie.length} minutes</p>
            <p>Language: ${movie.languages.join(', ')}</p>
            <p>Features: ${movie.movieFeatures.join(', ')}</p>
        `;
        movieList.appendChild(movieCard);

        const option = document.createElement('option');
        option.value = movie.id;
        option.textContent = movie.name;
        movieSelect.appendChild(option);
    });
}

// Fetch shows for a specific movie
async function fetchShows(movieId) {
    try {
        const response = await fetch(`${ENDPOINTS.shows}?movieId=${movieId}`);
        return await response.json();
    } catch (error) {
        console.error('Error fetching shows:', error);
        return [];
    }
}

// Populate show list based on selected movie
async function populateShows() {
    const selectedMovieId = parseInt(movieSelect.value);
    showSelect.innerHTML = '<option value="">Select a show</option>';
    if (selectedMovieId) {
        const shows = await fetchShows(selectedMovieId);
        shows.forEach(show => {
            const option = document.createElement('option');
            option.value = show.id;
            option.textContent = `${new Date(show.startTime).toLocaleString()} - ${show.language}`;
            showSelect.appendChild(option);
        });
    }
}

// Fetch seats for a specific show
async function fetchSeats(showId) {
    try {
        const response = await fetch(`${ENDPOINTS.seats}?showId=${showId}`);
        return await response.json();
    } catch (error) {
        console.error('Error fetching seats:', error);
        return [];
    }
}

// Populate seat selection
async function populateSeats() {
    const selectedShowId = parseInt(showSelect.value);
    seatSelect.innerHTML = '';
    if (selectedShowId) {
        const seats = await fetchSeats(selectedShowId);
        seats.forEach(seat => {
            const seatElement = document.createElement('div');
            seatElement.classList.add('seat');
            seatElement.textContent = seat.seatNumber;
            seatElement.dataset.id = seat.id;
            seatElement.dataset.type = seat.type;
            seatElement.dataset.price = seat.price;
            if (seat.status === 'AVAILABLE') {
                seatElement.addEventListener('click', toggleSeat);
            } else {
                seatElement.classList.add('unavailable');
            }
            seatSelect.appendChild(seatElement);
        });
    }
}

// Toggle seat selection
function toggleSeat(event) {
    event.target.classList.toggle('selected');
}

// Handle form submission
async function handleSubmit(event) {
    event.preventDefault();
    const selectedShowId = parseInt(showSelect.value);
    const selectedSeats = Array.from(seatSelect.querySelectorAll('.seat.selected')).map(seat => parseInt(seat.dataset.id));

    if (!selectedShowId || selectedSeats.length === 0) {
        alert('Please select a show and at least one seat.');
        return;
    }

    const bookingData = {
        showId: selectedShowId,
        userId: 1, // Replace with actual user ID once authentication is implemented
        showSeatIds: selectedSeats
    };

    try {
        const response = await fetch(ENDPOINTS.bookTicket, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(bookingData),
        });

        if (response.ok) {
            const ticket = await response.json();
            alert(`Booking Confirmed! Ticket ID: ${ticket.id}`);
            // Reset form and refresh data
            bookingForm.reset();
            populateMovies();
        } else {
            alert('Booking failed. Please try again.');
        }
    } catch (error) {
        console.error('Error booking ticket:', error);
        alert('An error occurred while booking. Please try again.');
    }
}

// Initialize the page
populateMovies();
movieSelect.addEventListener('change', populateShows);
showSelect.addEventListener('change', populateSeats);
bookingForm.addEventListener('submit', handleSubmit);
